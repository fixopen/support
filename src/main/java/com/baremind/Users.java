package com.baremind;

import com.baremind.algorithm.Securities;
import com.baremind.data.*;
import com.baremind.utils.DecodeObject;
import com.baremind.utils.Hex;
import com.baremind.utils.IdGenerator;
import com.baremind.utils.JPAEntry;
import com.google.gson.Gson;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/8/20 0020.
 */
@Path("user")
public class Users {
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@CookieParam("sessionId") String sessionId, @PathParam("id") Long id) {
        Response result = Response.status(401).build();
        if (JPAEntry.isLogining(sessionId)) {
            result = Response.status(404).build();
            User user = JPAEntry.getObject(User.class, "id", id);
            if (user != null) {
                result = Response.ok(user, MediaType.APPLICATION_JSON).build();
            }
        }
        return result;
    }


    @GET
    @Path("session")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCurrBySessionId(@CookieParam("sessionId") String sessionId) {
        Response result = Response.status(401).build();
        if (JPAEntry.isLogining(sessionId)) {
            result = Response.status(404).build();
            Account account= JPAEntry.getAccount(sessionId);
            if(account != null){
                User user = JPAEntry.getObject(User.class,"id", account.getSubjectId());
                if (user != null) {
                    Map map = new HashMap<>();
                    map.put("user",user);
                    map.put("account",account);
                    result = Response.ok(new Gson().toJson(map), MediaType.APPLICATION_JSON).build();
                }
            }
        }
        return result;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response postUser(@CookieParam("sessionId") String sessionId, User user) {
        Response result = Response.status(401).build();
        if (JPAEntry.isLogining(sessionId)) {
            user.setId(IdGenerator.getNewId());
            EntityManager em = JPAEntry.getEntityManager();
            em.getTransaction().begin();
            em.persist(user);
            Account account = new Account();
            account.setId(IdGenerator.getNewId());
            account.setSubjectId(user.getId());
            account.setSubjectType("Personal");
            account.setActive(0);
            account.setType(0);
            account.setLoginName(user.getName());
            account.setPassword(user.getName());
            em.persist(account);
            em.getTransaction().commit();
            result = Response.ok(account).build();
        }
        return result;
    }

    @GET
    @Path("page")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPage(@CookieParam("sessionId") String sessionId, @QueryParam("type") Integer type, @QueryParam("str") String str, @QueryParam("page") int page, @QueryParam("pageSize") int pageSize) {
        Response result = Response.status(401).build();
        if (JPAEntry.isLogining(sessionId)) {
            result = Response.status(404).build();



            if(pageSize == 0) pageSize = 10;
            if(page < 1) page = 1;

            int firstNum = (page - 1) * pageSize;

            Account currAccount= JPAEntry.getAccount(sessionId);

            String sql = "SELECT u FROM User u,Account a where a.subjectId = u.id ";


            if(type!=null){
                sql += " and a.type = "+type+"";
            }
            if(str!=null){
                //数据格式 例如： name::小学语文
                String s = str.split("::")[0];
                if(!"all".equals(s)){
                    if(str.split("::").length ==2){
                        String val = str.split("::")[1];
                        sql += " and u."+ s+" like "+"'%"+val+"%'";
                    }else {
                        sql += " and u."+ s+" like "+"''";
                    }
                }
            }
            EntityManager em = JPAEntry.getEntityManager();
            TypedQuery query = em.createQuery(sql,User.class);

            int allNum = query.getResultList().size(); //总条数

            int allPage = (allNum + pageSize - 1) / pageSize;

            List<User> list = query.setFirstResult(firstNum).setMaxResults(pageSize).getResultList();
            User user = null;

            for(User cc : list){
                Account account = JPAEntry.getObject(Account.class,"subjectId", cc.getId());
                cc.setAccount(account);

                int userType = cc.getAccount().getType(); //9版权审核人员，2版权登记(出版社)，-1管理员，0普通
                String typeStr = "";
                if(userType == 9){
                    typeStr = "版权审核人员";
                }
                if(userType == 2){
                    typeStr = "版权登记(出版社)";
                }
                if(userType == -1){
                    typeStr = "管理员";
                }
                if(userType == 0){
                    typeStr = "普通用户";
                }
                cc.getAccount().setTypeStr(typeStr);
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

    @POST
    @Path("create")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    public Response post(@Context HttpServletRequest request, @CookieParam("sessionId") String sessionId, User user) {



        Response result = Response.status(401).build();
        Account account = JPAEntry.getAccount(sessionId);
        request.getContextPath();
        if (account != null) {
            boolean isLogin = JPAEntry.isLogining(account);
            if (isLogin) {

                user = DecodeObject.decodeUTF8(user, User.class);
                Long userId = IdGenerator.getNewId();
                user.setId(userId);


                Account newAccount =new Account();
                newAccount.setId(IdGenerator.getNewId());
                newAccount.setSubjectId(user.getId());
                String t = request.getParameter("type.code");
                newAccount.setType(Integer.valueOf(request.getParameter("type.code")));




                EntityManager em = JPAEntry.getEntityManager();
                em.getTransaction().begin();

                em.persist(user);
                em.persist(newAccount);

                em.getTransaction().commit();



                result = Response.status(200).build();
            }
        }
        return result;
    }
}
