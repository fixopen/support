package com.baremind;

import com.baremind.algorithm.Securities;
import com.baremind.data.Account;
import com.baremind.data.Organization;
import com.baremind.data.User;
import com.baremind.utils.Hex;
import com.google.gson.Gson;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import java.lang.annotation.Annotation;
import java.net.URI;
import java.util.*;

/**
 * Created by fixopen on 16/8/15.
 */
@Path("sessions")
public class Sessions{
    private static final String PERSISTENCE_UNIT_NAME = "sd";
    private static EntityManagerFactory factory;

    @POST
    @Path("login")
    @Produces(MediaType.TEXT_PLAIN)
    public Response login(Account account) {
        Response response = null;
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();
//        Query q = em.createQuery("SELECT a FROM accounts a " +
//                "WHERE subject_type = :subjectType AND login_name = :loginName " +
//                "AND password = :password", account.getSubjectType(), account.getLoginName(), account.getPassword());
        String jpql = "SELECT a FROM Account a WHERE a.subjectType = :subjectType AND a.loginName = :loginName AND a.password = :password";
        List<Account> accounts = em.createQuery(jpql,Account.class)
                .setParameter("subjectType", account.getSubjectType())
                .setParameter("loginName", account.getLoginName())
                .setParameter("password", account.getPassword()).getResultList();
        int count = accounts.size();
        switch (count) {
            case 1: //ok
                Account findAccount = accounts.get(0);
                Date now = new Date();
                java.sql.Date sqlDate = new java.sql.Date(now.getTime());
                String nowString = now.toString();
                byte [] sessionId = Securities.digestor.digest(nowString);
                String sessionString = Hex.bytesToHex(sessionId);
                findAccount.setSessionId(sessionString);
                findAccount.setLastOpereationTime(sqlDate);
                findAccount.setPasswordFailedCount(0);
                findAccount.setLoginCount(findAccount.getLoginCount()+1);
                findAccount.setActive(1);
                em.persist(findAccount);

                String getUserOrOrg = "";
                switch (findAccount.getSubjectType()){
                    case Organization:
                        getUserOrOrg = "SELECT o FROM Organization o WHERE o.id= :id";
                        Organization org = em.createQuery(getUserOrOrg, Organization.class).setParameter("id", findAccount.getSubjectId()).getSingleResult();
                        response = Response.ok(new Gson().toJson(org)).cookie(new NewCookie("sessionId", sessionString, "/api/", null, null, -1, false)).build();
                        break;
                    case Personal:
                        getUserOrOrg = "SELECT u FROM User u WHERE u.id= :id";
                        User user = em.createQuery(getUserOrOrg, User.class).setParameter("id", findAccount.getSubjectId()).getSingleResult();
                        response = Response.ok(new Gson().toJson(user)).cookie(new NewCookie("sessionId", sessionString, "/api/", null, null, -1, false)).build();
                        break;
                }
                //now -> string -> sha1|md5 -> string -> account.sessionId -> persist
                //find user or organization
                //restore the session
                break;
            case 0: //login error
                response = Response.status(404).build();
                break;
            default: //internal error
                response = Response.status(500).build();
                break;
        }
        return response;
    }

    @POST
    @Path("loginOut")
    @Produces(MediaType.TEXT_PLAIN)
    public void logout(Account account) {
        //
    }
}
