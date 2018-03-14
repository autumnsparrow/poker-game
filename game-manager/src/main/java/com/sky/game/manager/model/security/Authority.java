package com.sky.game.manager.model.security;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.persistence.EntityManager;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(table = "security_authorities")

public class Authority {
	
    /**
     */
    @NotNull
    @Size(min = 2, max = 10)
    private String roleId;

    /**
     */
    @NotNull
    private String role;

    /**
     */
    @ManyToOne
    private Principal username;
}
