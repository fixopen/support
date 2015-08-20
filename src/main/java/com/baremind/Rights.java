package com.baremind;

import com.baremind.data.RightTransfer;
import com.baremind.utils.IdGenerator;
import com.baremind.utils.JPAEntry;

import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

/**
 * Created by Administrator on 2015/8/18 0018.
 */
@Path("rights")
public class Rights {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public RightTransfer post(@CookieParam("sessionId") String sessionId, RightTransfer rightTransfer) {
        if (JPAEntry.isLogining(sessionId)) {
            rightTransfer.setId(IdGenerator.getNewId());
            EntityManager em = JPAEntry.getEntityManager();
            em.getTransaction().begin();
            em.persist(rightTransfer);
            em.getTransaction().commit();
        }
        return null;
    }

}
