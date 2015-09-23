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
 * Created by fixopen on 16/8/15.
 */
@Path("copyrights")
public class Copyrights {
    public Copyright post() {
        return new Copyright();
    }

    public void revoke() {
        //
    }



    @GET
    @Path("page")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPage(@CookieParam("sessionId") String sessionId, @QueryParam("type") String type, @QueryParam("str") String str, @QueryParam("page") int page, @QueryParam("pageSize") int pageSize) {
        Response result = Response.status(401).build();
        if (JPAEntry.isLogining(sessionId)) {
            result = Response.status(404).build();



            if(pageSize == 0) pageSize = 10;
            if(page < 1) page = 1;

            int firstNum = (page - 1) * pageSize;

            Account currAccount= JPAEntry.getAccount(sessionId);

            String sql = "SELECT cr FROM Copyright cr,Resource r where cr.resourceId = r.id ";

            if(currAccount.getType() == 2){
                sql += " and r.ownerId = "+currAccount.getId()+"";
            }

            /*if(copyrightId > 0 || resourceId!=0){
                sql = sql + " and rt.copyrightId="+copyrightId+"";
            }*/


            EntityManager em = JPAEntry.getEntityManager();
            TypedQuery query = em.createQuery(sql,Copyright.class);

            int allNum = query.getResultList().size(); //总条数

            int allPage = (allNum + pageSize - 1) / pageSize;

            List<Copyright> list = query.setFirstResult(firstNum).setMaxResults(pageSize).getResultList();
            User user = null;
            Account account = null;
            UploadLog uploadLog = null;
            for(Copyright cc : list){
                Resource rt = JPAEntry.getObject(Resource.class,"id",cc.getResourceId());
                uploadLog = JPAEntry.getObject(UploadLog.class, "resourceNo", rt.getNo());

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd H:m:s");
                uploadLog.setTimeStr(dateFormat.format(uploadLog.getTime()));
                rt.setUploadLog(uploadLog);

                user = JPAEntry.getObject(User.class, "id", rt.getOwnerId());

                rt.setUser(user);
                cc.setResource(rt);

                int status = cc.getStatus();
                String statusStr = "";
                if(status == 1){
                    statusStr = "待审核";
                }

                if(status == 2){
                    statusStr = "通过";
                }

                if(status == -1){
                    statusStr = "不通过";
                }
                cc.setStatusStr(statusStr);
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

    @GET
    @Path("SPpage")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSPPage(@CookieParam("sessionId") String sessionId, @QueryParam("type") String type, @QueryParam("str") String str, @QueryParam("page") int page, @QueryParam("pageSize") int pageSize) {
        Response result = Response.status(401).build();
        if (JPAEntry.isLogining(sessionId)) {
            result = Response.status(404).build();



            if(pageSize == 0) pageSize = 10;
            if(page < 1) page = 1;

            int firstNum = (page - 1) * pageSize;

            Account currAccount= JPAEntry.getAccount(sessionId);

            String sql = "SELECT cr FROM Copyright cr,Resource r where cr.resourceId = r.id ";

            if(currAccount.getType() == 2){
                sql += " and r.ownerId = "+currAccount.getId()+"";
            }

            /*if(copyrightId > 0 || resourceId!=0){
                sql = sql + " and rt.copyrightId="+copyrightId+"";
            }*/


            EntityManager em = JPAEntry.getEntityManager();
            TypedQuery query = em.createQuery(sql,Copyright.class);

            int allNum = query.getResultList().size(); //总条数

            int allPage = (allNum + pageSize - 1) / pageSize;

            List<Copyright> list = query.setFirstResult(firstNum).setMaxResults(pageSize).getResultList();
            User user = null;
            Account account = null;
            UploadLog uploadLog = null;
            for(Copyright cc : list){
                Resource rt = JPAEntry.getObject(Resource.class,"id",cc.getResourceId());
                uploadLog = JPAEntry.getObject(UploadLog.class, "resourceNo", rt.getNo());

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd H:m:s");
                uploadLog.setTimeStr(dateFormat.format(uploadLog.getTime()));
                rt.setUploadLog(uploadLog);

                user = JPAEntry.getObject(User.class, "id", rt.getOwnerId());

                rt.setUser(user);
                cc.setResource(rt);

                int status = cc.getStatus();
                String statusStr = "";
                if(status == 1){
                    statusStr = "待审核";
                }

                if(status == 2){
                    statusStr = "通过";
                }

                if(status == -1){
                    statusStr = "不通过";
                }
                cc.setStatusStr(statusStr);
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
