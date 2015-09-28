package com.baremind;

import com.baremind.data.*;
import com.baremind.utils.JPAEntry;
import com.google.gson.Gson;
import org.glassfish.jersey.server.mvc.Template;
import org.glassfish.jersey.server.mvc.Viewable;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fixopen on 16/8/15.
 */
@Path("copyrights")
public class Copyrights {

    @GET
    @Path("page")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPage(@CookieParam("sessionId") String sessionId, @QueryParam("startTime") String startTime,@QueryParam("endTime") String endTime, @QueryParam("str") String str, @QueryParam("page") int page, @QueryParam("pageSize") int pageSize) throws ParseException {
        Response result = Response.status(401).build();
        if (JPAEntry.isLogining(sessionId)) {
            result = Response.status(404).build();



            if(pageSize == 0) pageSize = 10;
            if(page < 1) page = 1;

            int firstNum = (page - 1) * pageSize;

            Account currAccount= JPAEntry.getAccount(sessionId);

            String sql = "SELECT cr FROM Copyright cr,Resource r where cr.resourceId = r.id and cr.status = 2";

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

                sql += " and r.time between cast('"+startDate+"' as timestamp) and cast('"+endDate+"' as timestamp)";
            }
            if(str!=null){
                //数据格式 例如： name::小学语文
                String s = str.split("::")[0];
                if(!"all".equals(s)){
                    if(str.split("::").length ==2){
                        String val = str.split("::")[1];
                        sql += " and r."+ s+" like "+"'"+val+"%'";
                    }else {
                        sql += " and r."+ s+" like "+"''";
                    }
                }
            }

            /*if(copyrightId > 0 || resourceId!=0){
                sql = sql + " and rt.copyrightId="+copyrightId+"";
            }*/


            EntityManager em = JPAEntry.getEntityManager();
            TypedQuery query = em.createQuery(sql, Copyright.class);

            int allNum = query.getResultList().size(); //总条数

            int allPage = (allNum + pageSize - 1) / pageSize;

            List<Copyright> list = query.setFirstResult(firstNum).setMaxResults(pageSize).getResultList();
            User user = null;
            Account account = null;
            //UploadLog uploadLog = null;
            for(Copyright cc : list){
                Resource rt = JPAEntry.getObject(Resource.class,"id",cc.getResourceId());
                //uploadLog = JPAEntry.getObject(UploadLog.class, "resourceNo", rt.getNo());

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd H:m:s");
                rt.setTimeStr(dateFormat.format(rt.getTime()));


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
    public Response getSPPage(@CookieParam("sessionId") String sessionId, @QueryParam("type") Integer type, @QueryParam("str") String str, @QueryParam("page") int page, @QueryParam("pageSize") int pageSize) {
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
            if(type!=null){
                sql += " and cr.status = "+type+"";
            }
            if(str!=null){
                //数据格式 例如： name::小学语文
                String s = str.split("::")[0];
                if(!"all".equals(s)){
                    if(str.split("::").length ==2){
                        String val = str.split("::")[1];
                        sql += " and r."+ s+" like "+"'"+val+"%'";
                    }else {
                        sql += " and r."+ s+" like "+"''";
                    }
                }
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

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd H:m:s");
                rt.setTimeStr(dateFormat.format(rt.getTime()));

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


    private Copyright getById(String sessionId,Long copyrightId){
        Copyright copyright = JPAEntry.getObject(Copyright.class, "id", copyrightId);
        if(copyright == null){
            return null;
        }
        Account currAccount= JPAEntry.getAccount(sessionId);
        if(currAccount.getType() == 2){
            if(currAccount.getId() != copyright.getOwnerId()){
                return null;
            }
        }
        Resource resource = JPAEntry.getObject(Resource.class, "id", copyright.getResourceId());
        if(resource == null){
            return null;
        }
        copyright.setResource(resource);

        UploadLog uploadLog = JPAEntry.getObject(UploadLog.class, "resourceNo", resource.getNo());

        resource.setUploadLog(uploadLog);
        User user = JPAEntry.getObject(User.class, "id", resource.getOwnerId());
        resource.setUser(user);


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd H:m:s");
        resource.setTimeStr(dateFormat.format(resource.getTime()));


        int status = copyright.getStatus();
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
        copyright.setStatusStr(statusStr);
        return copyright;
    }

    @GET
    @Path("info")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@CookieParam("sessionId") String sessionId, @QueryParam("copyrightId") Long copyrightId) {
        Response result = Response.status(401).build();
        if (JPAEntry.isLogining(sessionId)) {
            result = Response.status(404).build();


            /*Map<String,Object> map = new HashMap<>();
            map.put("data",copyright);
            map.put("meta",allNum);*/
            Copyright copyright = this.getById(sessionId, copyrightId);
            result = Response.ok(new Gson().toJson(copyright)).build();
        }
        return result;
    }

    @GET
    @Path("infoJSP")
    @Template
    @Produces(MediaType.TEXT_HTML)
    public Viewable get(@Context HttpServletRequest request,@CookieParam("sessionId") String sessionId, @QueryParam("copyrightId") Long copyrightId) {
        if (!JPAEntry.isLogining(sessionId)) {
           return new Viewable("/login",null);
        }
        Copyright copyright = this.getById(sessionId, copyrightId);
        request.setAttribute("copyright",copyright);
        return new Viewable("/copyrightDetail", null);
    }


    @GET
    @Path("delete/{copyrightId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@CookieParam("sessionId") String sessionId, @PathParam("copyrightId") Long copyrightId) {
        Response result = Response.status(401).build();
        if (JPAEntry.isLogining(sessionId)) {
            result = Response.status(404).build();

            Copyright copyright = JPAEntry.getObject(Copyright.class, "id", copyrightId);
            if(copyright == null){
                return null;
            }

            Account currAccount= JPAEntry.getAccount(sessionId);
            if(currAccount.getType() == 2){
                if(currAccount.getId() != copyright.getOwnerId()){
                    return null;
                }
            }

            Resource resource = JPAEntry.getObject(Resource.class, "id", copyright.getResourceId());
            if(resource == null){
                return null;
            }


            UploadLog uploadLog = JPAEntry.getObject(UploadLog.class, "resourceNo", resource.getNo());
            EntityManager em = JPAEntry.getEntityManager();
            em.getTransaction().begin();

            Copyright copyrightR = em.find(Copyright.class, copyright.getId());
            Resource resourceR = em.find(Resource.class, resource.getId());
            UploadLog uploadLogR = em.find(UploadLog.class, uploadLog.getId());
            em.remove(copyrightR);
            em.remove(resourceR);
            em.remove(uploadLogR);
            em.getTransaction().commit();


            result = Response.status(200).build();
        }
        return result;
    }

    @GET
    @Path("audit/{copyrightId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@CookieParam("sessionId") String sessionId, @PathParam("copyrightId") Long copyrightId, @QueryParam("status") Integer status) {
        Response result = Response.status(401).build();
        if (JPAEntry.isLogining(sessionId)) {
            result = Response.status(404).build();

            Copyright copyright = this.getById(sessionId, copyrightId);

            EntityManager em = JPAEntry.getEntityManager();
            em.getTransaction().begin();

            copyright.setStatus(status);

            em.merge(copyright);

            em.getTransaction().commit();

            result = Response.status(200).build();
        }
        return result;
    }


}
