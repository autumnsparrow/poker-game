// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.sky.game.manager.activerecord;

import com.sky.game.manager.activerecord.UserActiveDayliyStatics;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

privileged aspect UserActiveDayliyStatics_Roo_Finder {
    
    public static Long UserActiveDayliyStatics.countFindUserActiveDayliyStaticsesByChannelId(int channelId) {
        EntityManager em = UserActiveDayliyStatics.entityManager();
        TypedQuery q = em.createQuery("SELECT COUNT(o) FROM UserActiveDayliyStatics AS o WHERE o.channelId = :channelId", Long.class);
        q.setParameter("channelId", channelId);
        return ((Long) q.getSingleResult());
    }
    
    public static Long UserActiveDayliyStatics.countFindUserActiveDayliyStaticsesByStaticsDateBetween(Date minStaticsDate, Date maxStaticsDate) {
        if (minStaticsDate == null) throw new IllegalArgumentException("The minStaticsDate argument is required");
        if (maxStaticsDate == null) throw new IllegalArgumentException("The maxStaticsDate argument is required");
        EntityManager em = UserActiveDayliyStatics.entityManager();
        TypedQuery q = em.createQuery("SELECT COUNT(o) FROM UserActiveDayliyStatics AS o WHERE o.staticsDate BETWEEN :minStaticsDate AND :maxStaticsDate", Long.class);
        q.setParameter("minStaticsDate", minStaticsDate);
        q.setParameter("maxStaticsDate", maxStaticsDate);
        return ((Long) q.getSingleResult());
    }
    
    public static TypedQuery<UserActiveDayliyStatics> UserActiveDayliyStatics.findUserActiveDayliyStaticsesByChannelId(int channelId) {
        EntityManager em = UserActiveDayliyStatics.entityManager();
        TypedQuery<UserActiveDayliyStatics> q = em.createQuery("SELECT o FROM UserActiveDayliyStatics AS o WHERE o.channelId = :channelId", UserActiveDayliyStatics.class);
        q.setParameter("channelId", channelId);
        return q;
    }
    
    public static TypedQuery<UserActiveDayliyStatics> UserActiveDayliyStatics.findUserActiveDayliyStaticsesByChannelId(int channelId, String sortFieldName, String sortOrder) {
        EntityManager em = UserActiveDayliyStatics.entityManager();
        StringBuilder queryBuilder = new StringBuilder("SELECT o FROM UserActiveDayliyStatics AS o WHERE o.channelId = :channelId");
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            queryBuilder.append(" ORDER BY ").append(sortFieldName);
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                queryBuilder.append(" ").append(sortOrder);
            }
        }
        TypedQuery<UserActiveDayliyStatics> q = em.createQuery(queryBuilder.toString(), UserActiveDayliyStatics.class);
        q.setParameter("channelId", channelId);
        return q;
    }
    
    public static TypedQuery<UserActiveDayliyStatics> UserActiveDayliyStatics.findUserActiveDayliyStaticsesByStaticsDateBetween(Date minStaticsDate, Date maxStaticsDate) {
        if (minStaticsDate == null) throw new IllegalArgumentException("The minStaticsDate argument is required");
        if (maxStaticsDate == null) throw new IllegalArgumentException("The maxStaticsDate argument is required");
        EntityManager em = UserActiveDayliyStatics.entityManager();
        TypedQuery<UserActiveDayliyStatics> q = em.createQuery("SELECT o FROM UserActiveDayliyStatics AS o WHERE o.staticsDate BETWEEN :minStaticsDate AND :maxStaticsDate", UserActiveDayliyStatics.class);
        q.setParameter("minStaticsDate", minStaticsDate);
        q.setParameter("maxStaticsDate", maxStaticsDate);
        return q;
    }
    
    public static TypedQuery<UserActiveDayliyStatics> UserActiveDayliyStatics.findUserActiveDayliyStaticsesByStaticsDateBetween(Date minStaticsDate, Date maxStaticsDate, String sortFieldName, String sortOrder) {
        if (minStaticsDate == null) throw new IllegalArgumentException("The minStaticsDate argument is required");
        if (maxStaticsDate == null) throw new IllegalArgumentException("The maxStaticsDate argument is required");
        EntityManager em = UserActiveDayliyStatics.entityManager();
        StringBuilder queryBuilder = new StringBuilder("SELECT o FROM UserActiveDayliyStatics AS o WHERE o.staticsDate BETWEEN :minStaticsDate AND :maxStaticsDate");
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            queryBuilder.append(" ORDER BY ").append(sortFieldName);
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                queryBuilder.append(" ").append(sortOrder);
            }
        }
        TypedQuery<UserActiveDayliyStatics> q = em.createQuery(queryBuilder.toString(), UserActiveDayliyStatics.class);
        q.setParameter("minStaticsDate", minStaticsDate);
        q.setParameter("maxStaticsDate", maxStaticsDate);
        return q;
    }
    
}
