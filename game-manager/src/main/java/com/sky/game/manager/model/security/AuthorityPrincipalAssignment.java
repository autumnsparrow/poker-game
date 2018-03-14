package com.sky.game.manager.model.security;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.EntityManager;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(table = "security_role_assignments")

public class AuthorityPrincipalAssignment {
	
    /**
     */
    @ManyToOne
    private Principal username;

    /**
     */
    @ManyToOne
    private Authority roleId;
}
