package com.baremind.utils;

import com.baremind.data.Account;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Date;
import java.util.List;

/**
 * Created by fixopen on 18/8/15.
 */
public class JPAEntry {
    public interface TouchFunction {
        void touch(Account a);
    }

    private static final String PERSISTENCE_UNIT_NAME = "sd";
    private static EntityManagerFactory factory;

    public static EntityManager getEntityManager() {
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        return factory.createEntityManager();
    }

    public static Account getAccount(String sessionId) {
        Account result = null;
        EntityManager em = getEntityManager();
        String jpql = "SELECT a FROM Account a WHERE a.sessionId = :sessionId ";
        List<Account> accounts = em.createQuery(jpql, Account.class)
            .setParameter("sessionId", sessionId)
            .getResultList();
        int count = accounts.size();
        switch (count) {
            case 1: //ok
                result = accounts.get(0);
                break;

        }
        return result;
    }

    public static boolean isLogining(Account a) {
        return isLogining(a, (Account account) -> {});
    }

    public static boolean isLogining(Account account, TouchFunction touchFunction) {
        boolean result = false;
        if (account.getActive() == 1) {
            Date now = new Date();
            Date lastOperationTime = account.getLastOpereationTime();
            if (now.getTime() - lastOperationTime.getTime() < 30 * 60 * 1000) {
                EntityManager em = getEntityManager();
                em.getTransaction().begin();
                account.setLastOpereationTime(now);
                touchFunction.touch(account);
                em.persist(account);
                em.getTransaction().commit();
                result = true;
            }
        }
        return result;
    }

    public static boolean isLogining(String sessionId) {
        return isLogining(sessionId, (Account a) -> {});
    }

    public static boolean isLogining(String sessionId, TouchFunction touchFunction) {
        boolean result = false;
        Account account = getAccount(sessionId);
        if (account != null) {
            result = isLogining(account, touchFunction);
        }
        return result;
    }

    public static boolean hasKickOtherPermission(String sessionId) {
        return false;
    }
}
