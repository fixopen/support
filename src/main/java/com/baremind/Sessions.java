package com.baremind;

import com.baremind.algorithm.Securities;
import com.baremind.data.Account;
import com.baremind.data.Organization;
import com.baremind.data.User;
import com.baremind.utils.Hex;
import com.baremind.utils.JPAEntry;

import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fixopen on 16/8/15.
 */
@Path("sessions")
public class Sessions{
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(Account account) {
        Response response = Response.status(400).build();
        if (account.getSubjectType() != null
            && account.getLoginName() != null
            && account.getPassword() != null) {
            Map<String, Object> conditions = new HashMap<>(3);
            conditions.put("subjectType", account.getSubjectType());
            conditions.put("loginName", account.getLoginName());
            conditions.put("password", account.getPassword());
            List<Account> accounts = JPAEntry.getList(Account.class, conditions);
            switch (accounts.size()) {
                case 1: //ok
                    EntityManager em = JPAEntry.getEntityManager();
                    em.getTransaction().begin();
                    Account findAccount = accounts.get(0);
                    Date now = new Date();
                    String nowString = now.toString();
                    byte[] sessionId = Securities.digestor.digest(nowString);
                    String sessionString = Hex.bytesToHex(sessionId);
                    findAccount.setSessionId(sessionString);
                    findAccount.setLastOpereationTime(now);
                    findAccount.setPasswordFailedCount(0);
                    findAccount.setLoginCount(findAccount.getLoginCount() + 1);
                    findAccount.setActive(1);
                    em.merge(findAccount);
                    em.getTransaction().commit();

                    String getUserOrOrg = "";
                    switch (findAccount.getSubjectType()){
                        case "Organization":
                            Organization organization = JPAEntry.getObject(Organization.class, "id", findAccount.getSubjectId());
                            response = Response.ok(organization, MediaType.APPLICATION_JSON).cookie(new NewCookie("sessionId", sessionString, "/api/", null, null, -1, false)).build();
                            break;
                        case "Personal":
                            User user = JPAEntry.getObject(User.class, "id", findAccount.getSubjectId());
                            response = Response.ok(user, MediaType.APPLICATION_JSON).cookie(new NewCookie("sessionId", sessionString, "/api/", null, null, -1, false)).build();
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
