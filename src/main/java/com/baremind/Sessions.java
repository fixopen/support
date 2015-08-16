package com.baremind;

import com.baremind.data.Account;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.Path;

/**
 * Created by fixopen on 16/8/15.
 */
@Path("sessions")
public class Sessions {
    private static final String PERSISTENCE_UNIT_NAME = "sd";
    private static EntityManagerFactory factory;

    public String login(Account account) {
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();
//        Query q = em.createQuery("SELECT a FROM accounts a WHERE subject_type = :subjectType AND login_name = :loginName AND password = :password", account.getSubjectType(), account.getLoginName(), account.getPassword());
//        List<Account> accounts = q.getResultList();
//        int count = accounts.size();
//        switch (count) {
//            case 1: //ok
//                Account findAccount = accounts.get(0);
//                //now -> string -> sha1|md5 -> string -> account.sessionId -> persist
//                //find user or organization
//                //restore the session
//                break;
//            case 0: //login error
//                break;
//            default: //internal error
//                break;
//        }
        return "";
    }
    public void logout() {
        //ceshi
    }
}
