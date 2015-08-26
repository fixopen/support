package com.baremind;

import com.baremind.algorithm.Securities;
import com.baremind.data.Account;
import com.baremind.data.Organization;
import com.baremind.data.User;
import com.baremind.utils.Hex;
import com.baremind.utils.JPAEntry;
import com.google.gson.Gson;

import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by fixopen on 16/8/15.
 */
@Path("sessions")
public class Sessions{
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(Account account) {
        Response response = Response.status(401).build();
        EntityManager em = JPAEntry.getEntityManager();
        String jpql = "SELECT a FROM Account a WHERE a.subjectType = :subjectType AND  a.loginName = :loginName AND a.password = :password";
        List<Account> accounts = new ArrayList<Account>();
        if(account.getSubjectType()!=null&&account.getLoginName()!=null&& account.getPassword()!=null){
            accounts = em.createQuery(jpql,Account.class)
                    .setParameter("subjectType", account.getSubjectType())
                    .setParameter("loginName", account.getLoginName())
                    .setParameter("password", account.getPassword()).getResultList();
        }
        int count = accounts.size();
        switch (count) {
            case 1: //ok
                em.getTransaction().begin();
                Account findAccount = accounts.get(0);
                Date now = new Date();
                String nowString = now.toString();
                byte [] sessionId = Securities.digestor.digest(nowString);
                String sessionString = Hex.bytesToHex(sessionId);
                findAccount.setSessionId(sessionString);
                findAccount.setLastOpereationTime(now);
                findAccount.setPasswordFailedCount(0);
                findAccount.setLoginCount(findAccount.getLoginCount()+1);
                findAccount.setActive(1);
                em.merge(findAccount);
                em.getTransaction().commit();

                String getUserOrOrg = "";
                switch (findAccount.getSubjectType()){
                    case "Organization":
                        getUserOrOrg = "SELECT o FROM Organization o WHERE o.id= :id";
                        Organization org = em.createQuery(getUserOrOrg, Organization.class).setParameter("id", findAccount.getSubjectId()).getSingleResult();
                        response = Response.ok(new Gson().toJson(org), MediaType.APPLICATION_JSON).cookie(new NewCookie("sessionId", sessionString, "/api/", null, null, -1, false)).build();
                        break;
                    case "Personal":
                        getUserOrOrg = "SELECT u FROM User u WHERE u.id= :id";
                        User user = em.createQuery(getUserOrOrg, User.class).setParameter("id", findAccount.getSubjectId()).getSingleResult();
//                        List<UserAct> user = em.createQuery(getUserOrOrg, UserAct.class).setParameter("id", findAccount.getSubjectId()).getResultList();
                        response = Response.ok(new Gson().toJson(user),MediaType.APPLICATION_JSON).cookie(new NewCookie("sessionId", sessionString, "/api/", null, null, -1, false)).build();
                        break;
                }
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

    @DELETE
    @Path("{sessionId}")
    public Response logout(@CookieParam("sessionId") String cookieSessionId, @PathParam("sessionId") String sessionId) {
        Response response = null;
        if (sessionId.equals("me")) {
            sessionId = cookieSessionId;
        }
        if (sessionId.equals(cookieSessionId)) {
            //logout self
            if (JPAEntry.isLogining(sessionId, (Account a) -> {
                a.setActive(0);
            })) {
                response = Response.ok().build();
            }
        } else {
            //kick other account
            if (JPAEntry.hasKickOtherPermission(cookieSessionId)) {
                //ok
                //admin 登出别人
            } else {
                response = Response.status(401).build();
            }
        }
        return response;
    }

}
