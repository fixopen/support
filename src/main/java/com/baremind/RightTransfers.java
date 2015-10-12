package com.baremind;

import com.baremind.data.*;
import com.baremind.utils.JPAEntry;
import com.google.gson.Gson;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

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

    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    @GET
    @Path("page")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPage(@CookieParam("sessionId") String sessionId, @QueryParam("startTime") String startTime,@QueryParam("endTime") String endTime, @QueryParam("str") String str,  @QueryParam("page") int page, @QueryParam("pageSize") int pageSize) {
        Response result = Response.status(401).build();
        if (JPAEntry.isLogining(sessionId)) {
            result = Response.status(404).build();


            if(pageSize == 0) pageSize = 10;
            if(page < 1) page = 1;

            int firstNum = (page - 1) * pageSize;

            Account currAccount= JPAEntry.getAccount(sessionId);

            String sql = "SELECT rt FROM RightTransfer rt,Copyright cr,Resource r where rt.copyrightId=cr.id and cr.resourceId = r.id";

            if(currAccount.getType() == 2){
                sql += " and r.ownerId = "+currAccount.getId()+"";
            }


            if(startTime!=null){

                Date startDate = null;
                Date endDate = null;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    startDate = sdf.parse(startTime + " 00:00:00");
                    if(endTime ==null || "".equals(endTime)){
                        endDate   =  new Date();
                    }else {
                        endDate = sdf.parse(endTime + " 23:59:59");
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //

                sql += " and rt.time between cast('"+startDate+"' as timestamp) and cast('"+endDate+"' as timestamp)";
            }

            if(str!=null){
                //数据格式 例如： resourceName::小学语文
                String s = str.split("::")[0];
                if(str.split("::").length ==2){
                    String val = str.split("::")[1];
                    if(!"all".equals(s)){
                        if ("resourceName".equals(s)) {
                            sql += " and r.name" + " like " + "'%" + val + "%'";
                        }
                        if ("no".equals(s)) {
                            Long id = Long.parseLong(val);
                            sql += " and rt.no" + "=" + id;
                        }
                    }else{
                        if(isInteger(val)==true){
                            sql += " and (r.name like "+"'%"+val+"%'"
                                    +" or rt.no like "+"'%"+val+"%'"
                                    +")";
                        }else {
                            sql += " and (r.name like "+"'%"+val+"%'"
                                    +")";
                        }
                    }

                }else {
                    if(!"all".equals(s)){
                        sql += " and r."+ s+" like "+"''";
                    }
                }
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
