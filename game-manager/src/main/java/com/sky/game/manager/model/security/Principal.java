package com.sky.game.manager.model.security;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(table="security_principles")

public class Principal {
	
    /**
     */
    @NotNull
    @Size(min = 3, max = 50)
    private String username;

    /**
     */
    @NotNull
    @Size(min = 3, max = 50)
    private String password;

    /**
     */
    private Boolean enabled;
}
