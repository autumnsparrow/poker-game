package com.sky.game.manager.remote.activerecord;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooJpaActiveRecord(versionField = "", sequenceName = "", table = "user_account")
//@RooDbManaged(automaticallyDelete = true)
@RooToString(excludeFields = { "eventLogs", "exchangeTradeLogs", "freezes", "freezes1", "pointCards", "storeTradeLogs", "userAchievements", "userBanks", "userDepositBoxes", "userExtras", "userItemLogs", "userItemss", "userPropertiesLogs", "userRewards", "userTasks", "userTokens", "userVips" })

public class UserAccount {

	

    @Column(name = "name", length = 50)
    @NotNull
    private String name;

    @Column(name = "nick_name", length = 50)
    private String nickName;

    @Column(name = "user_password", length = 128)
    private String userPassword;

    @Column(name = "update_time")
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Calendar updateTime;

    @Column(name = "create_time")
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Calendar createTime;

    @Column(name = "user_type")
    private Integer userType;

    @Column(name = "channel_id")
    private Long channelId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Calendar getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Calendar updateTime) {
        this.updateTime = updateTime;
    }

    public Calendar getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Calendar createTime) {
        this.createTime = createTime;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }
}
