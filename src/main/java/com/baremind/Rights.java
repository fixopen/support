package com.baremind;

import com.baremind.data.Account;
import com.baremind.data.Copyright;
import com.baremind.data.RightTransfer;
import com.baremind.utils.IdGenerator;
import com.baremind.utils.JPAEntry;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2015/8/18 0018.
 */
@Path("rights")
public class Rights {
    //{"bookId": "resourceNo", "accountId": "", "amount": nnn, "expiration": "srsalj" ,"accountSubjectType":""}
//        {"bookId":-1226244096,"amount":1,"expiration":"2015-01-01"}
    //resurce--> copyrightid
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response post(@CookieParam("sessionId") String sessionId,HttpServletRequest request) {
        Response result = Response.status(401).build();
        EntityManager em = JPAEntry.getEntityManager();
        if (JPAEntry.isLogining(sessionId)) {
            Account account = JPAEntry.getAccount(sessionId);
            String sql = "SELECT c FROM Copyright c  WHERE c.resourceId = :bookId ";
            Long bookId = Long.parseLong(request.getParameter("bookId")) ;
            Copyright copyright =  em.createQuery(sql,Copyright.class).setParameter("bookId",bookId).getSingleResult();

            RightTransfer rightTransfer = new RightTransfer();
            rightTransfer.setId(IdGenerator.getNewId());
            rightTransfer.setNo(rightTransfer.toString());
            rightTransfer.setTime(new Date());
            rightTransfer.setCopyrightId(copyright.getId());
            rightTransfer.setFromId(account.getId());
            rightTransfer.setToId(Long.parseLong(request.getParameter("bookId")));
            rightTransfer.setAmount(Integer.parseInt(request.getParameter("amount")));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
            Date date = null;
            try {
                date = sdf.parse(request.getParameter("expiration"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            rightTransfer.setExpiration(date);
//                rightTransfer.setRightTypeId();
//                rightTransfer.setFromId();
//                rightTransfer.setToId();
//                rightTransfer.setAmount(Integer.parseInt(request.getParameter("amount")));
//                rightTransfer.setExpiration();
//            }

//            rightTransfer.setId(IdGenerator.getNewId());
//            EntityManager em = JPAEntry.getEntityManager();
            em.getTransaction().begin();
            em.persist(rightTransfer);
            em.getTransaction().commit();

        }
        return result;
    }

}
