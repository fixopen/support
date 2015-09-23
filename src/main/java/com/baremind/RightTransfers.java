package com.baremind;

import com.baremind.data.*;
import com.baremind.utils.JPAEntry;
import com.google.gson.Gson;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/8/25 0025.
 */
@Path("rightTransfers")
public class RightTransfers {
    @GET
    @Path("{no}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response post(@CookieParam("sessionId") String sessionId, @PathParam("no") String no) {
        Response result = Response.status(401).build();
        if (JPAEntry.isLogining(sessionId)) {
            result = Response.status(404).build();
            List<Resource> resource = JPAEntry.getList(Resource.class, "no", no);
            if (resource != null) {
                Copyright copyright = JPAEntry.getObject(Copyright.class, "resourceId", resource.get(0).getId());
                if (copyright != null) {
                    List<RightTransfer> list = JPAEntry.getList(RightTransfer.class, "copyrightId", copyright.getId());
                    result = Response.ok(new Gson().toJson(list)).build();
                }
            }
        }
        return result;
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@CookieParam("sessionId") String sessionId) {
        Response result = Response.status(401).build();
        if (JPAEntry.isLogining(sessionId)) {
            result = Response.status(404).build();
            String sql = "SELECT r FROM RightTransfer r ";
            EntityManager em = JPAEntry.getEntityManager();
            List<RightTransfer> list = em.createQuery(sql, RightTransfer.class).getResultList();
            result = Response.ok(new Gson().toJson(list)).build();
        }
        return result;
    }

    @GET
    @Path("page")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPage(@CookieParam("sessionId") String sessionId, @QueryParam("resourceId") String resourceIdStr, @QueryParam("page") int page, @QueryParam("pageSize") int pageSize) {
        Response result = Response.status(401).build();
        if (JPAEntry.isLogining(sessionId)) {
            result = Response.status(404).build();

            if(null == resourceIdStr || "".equals(resourceIdStr)){
                resourceIdStr = "0";
            }

            Long resourceId = Long.parseLong(resourceIdStr);

            Resource r = JPAEntry.getObject(Resource.class, "no", resourceId+"");
            Copyright cr = null;
            if (r != null) {
                cr = JPAEntry.getObject(Copyright.class, "resourceId", r.getId()+"");
            }

            Long copyrightId = 0l;
            if(cr != null){
                copyrightId = cr.getId();
            }
            if(pageSize == 0) pageSize = 10;
            if(page < 1) page = 1;

            int firstNum = (page - 1) * pageSize;

            String sql = "SELECT rt FROM RightTransfer rt where 1=1";
            if(copyrightId > 0 || resourceId!=0){
                sql = sql + " and rt.copyrightId="+copyrightId+"";
            }


            EntityManager em = JPAEntry.getEntityManager();
            TypedQuery query = em.createQuery(sql,RightTransfer.class);

            int allNum = query.getResultList().size(); //总条数

            int allPage = (allNum + pageSize - 1) / pageSize;

            List<RightTransfer> list = query.setFirstResult(firstNum).setMaxResults(pageSize).getResultList();
            User user = null;
            Account account = null;
            for(RightTransfer rt : list){
                String toName = "";
                String fromName = "";
                account = JPAEntry.getObject(Account.class, "id", rt.getFromId());

                if(account == null){
                    fromName = "不存在";
                    rt.setFromName(fromName);

                }else {

                    user = JPAEntry.getObject(User.class, "id", account.getSubjectId());

                    if (user == null) {
                        fromName = "不存在";
                    } else {
                        fromName = user.getName();
                    }

                    rt.setFromName(fromName);
                }

                account = JPAEntry.getObject(Account.class, "id", rt.getToId());
                if(account == null){
                    toName = "不存在";
                    rt.setToName(toName);

                }else {
                    user = JPAEntry.getObject(User.class, "id", account.getSubjectId());


                    if (user == null) {
                        toName = "不存在";
                    } else {
                        toName = user.getName();
                    }

                    rt.setToName(toName);
                }
                String resourceName = "不存在";
                Copyright copyright = JPAEntry.getObject(Copyright.class,"id",rt.getCopyrightId());
                if(copyright == null){
                    rt.setResourceName(resourceName);
                    continue;
                }

                Resource resource = JPAEntry.getObject(Resource.class, "id", copyright.getResourceId());
                if(resource == null){
                    rt.setResourceName(resourceName);
                    continue;
                }
                resourceName = resource.getName();
                rt.setResourceName(resourceName);

                rt.setBookNo(resource.getNo());

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd H:m:s");
                rt.setTimeStr(dateFormat.format(rt.getTime()));

            }

            Map<String,Object> map = new HashMap<>();
            map.put("list",list);
            map.put("allNum",allNum);
            map.put("pageSize",pageSize);
            map.put("allPage",allPage);

            result = Response.ok(new Gson().toJson(map)).build();
        }
        return result;
    }
}
