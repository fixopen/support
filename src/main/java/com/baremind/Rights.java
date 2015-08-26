package com.baremind;

import com.baremind.data.Account;
import com.baremind.data.Copyright;
import com.baremind.data.Resource;
import com.baremind.data.RightTransfer;
import com.baremind.utils.IdGenerator;
import com.baremind.utils.JPAEntry;

import javax.persistence.EntityManager;
import javax.ws.rs.*;
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
    //{"bookNo": "resourceNo", "accountId": "", "amount": nnn, "expiration": "srsalj" ,"accountSubjectType":""}
//        {"bookId":-1226244096,"amount":1,"expiration":"2015-01-01"}
    //resurce--> copyrightid
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response post(@CookieParam("sessionId") String sessionId, RightTransfer rightTransfer) {
        //request 取值
        Response result = Response.status(401).build();
        EntityManager em = JPAEntry.getEntityManager();
        if (JPAEntry.isLogining(sessionId)) {
            Account account = JPAEntry.getAccount(sessionId);
            Resource r = JPAEntry.getObject(Resource.class, "Resource", "no", rightTransfer.getBookNo());
            if (r != null) {
                Copyright copyright = JPAEntry.getObject(Copyright.class, "Copyright", "resourceId", r.getId());
                rightTransfer.setId(IdGenerator.getNewId());
                rightTransfer.setNo(rightTransfer.getId().toString());
                rightTransfer.setTime(new Date());
                rightTransfer.setCopyrightId(copyright.getId());
                rightTransfer.setRightTypeId(10000l);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
                Date date = null;
                try {
                    date = sdf.parse(rightTransfer.getExpirationTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                rightTransfer.setExpiration(date);
                em.getTransaction().begin();
                em.persist(rightTransfer);
                em.getTransaction().commit();
                result = Response.ok(rightTransfer).build();
            }
        }
        return result;
    }

}
