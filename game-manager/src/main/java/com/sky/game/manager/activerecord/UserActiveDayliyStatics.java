package com.sky.game.manager.activerecord;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.EntityManager;
import javax.persistence.Enumerated;
import javax.persistence.Column;
import javax.persistence.PersistenceContext;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(table = "user_active_dayliy_statics", finders = { "findUserActiveDayliyStaticsesByStaticsDateBetween", "findUserActiveDayliyStaticsesByChannelId" })

public class UserActiveDayliyStatics {
	
	
	
    /**
     */
    @Column(name = "day2_active")
    private int day2Active;

    /**
     */
    @Column(name = "day3_active")
    private int day3Active;

    /**
     */
    @Column(name = "day7_active")
    private int day7Active;

    /**
     */
    @Column(name = "day30_active")
    private int day30Active;

    /**
     */
    @Column(name = "day2_active_v")
    private int day2ActiveV;

    /**
     */
    @Column(name = "day3_active_v")
    private int day3ActiveV;

    /**
     */
    @Column(name = "day7_active_v")
    private int day7ActiveV;

    /**
     */
    @Column(name = "day30_active_v")
    private int day30ActiveV;

    /**
     */
    @Column(name = "charge_money_amount")
    private long chargeMoneyAmount;

    /**
     */
    @Column(name = "statics_date")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date staticsDate;

    @Column(name = "channel_id")
    private int channelId;
}
