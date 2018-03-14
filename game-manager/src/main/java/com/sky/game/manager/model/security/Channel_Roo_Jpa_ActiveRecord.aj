// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.sky.game.manager.model.security;

import com.sky.game.manager.model.security.Channel;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

privileged aspect Channel_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager Channel.entityManager;
    
    public static final List<String> Channel.fieldNames4OrderClauseFilter = java.util.Arrays.asList("channelId", "createTime");
    
    public static final EntityManager Channel.entityManager() {
        EntityManager em = new Channel().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Channel.countChannels() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Channel o", Long.class).getSingleResult();
    }
    
    public static List<Channel> Channel.findAllChannels() {
        return entityManager().createQuery("SELECT o FROM Channel o", Channel.class).getResultList();
    }
    
    public static List<Channel> Channel.findAllChannels(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Channel o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Channel.class).getResultList();
    }
    
    public static Channel Channel.findChannel(Long id) {
        if (id == null) return null;
        return entityManager().find(Channel.class, id);
    }
    
    public static List<Channel> Channel.findChannelEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Channel o", Channel.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    public static List<Channel> Channel.findChannelEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Channel o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Channel.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void Channel.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Channel.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Channel attached = Channel.findChannel(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Channel.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Channel.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public Channel Channel.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Channel merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}