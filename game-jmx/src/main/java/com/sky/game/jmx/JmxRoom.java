package com.sky.game.jmx;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(finders = { "findJmxRoomsByChannelIdEqualsAndRoomIdEqualsAndVipNot" })
public class JmxRoom {

    /**
     */
    private Long channelId;

    /**
     */
    private Long roomId;

    /**
     */
    private Boolean vip;

    /**
     */
    private String content;
}
