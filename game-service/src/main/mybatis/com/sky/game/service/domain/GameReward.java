package com.sky.game.service.domain;

import java.util.Date;

public class GameReward {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column game_reward.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column game_reward.user_id
     *
     * @mbggenerated
     */
    private Long userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column game_reward.room_id
     *
     * @mbggenerated
     */
    private Long roomId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column game_reward.user_item_log_id
     *
     * @mbggenerated
     */
    private Long userItemLogId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column game_reward.user_property_log_id
     *
     * @mbggenerated
     */
    private Long userPropertyLogId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column game_reward.create_time
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column game_reward.rank
     *
     * @mbggenerated
     */
    private Integer rank;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column game_reward.team_id
     *
     * @mbggenerated
     */
    private Long teamId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column game_reward.id
     *
     * @return the value of game_reward.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column game_reward.id
     *
     * @param id the value for game_reward.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column game_reward.user_id
     *
     * @return the value of game_reward.user_id
     *
     * @mbggenerated
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column game_reward.user_id
     *
     * @param userId the value for game_reward.user_id
     *
     * @mbggenerated
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column game_reward.room_id
     *
     * @return the value of game_reward.room_id
     *
     * @mbggenerated
     */
    public Long getRoomId() {
        return roomId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column game_reward.room_id
     *
     * @param roomId the value for game_reward.room_id
     *
     * @mbggenerated
     */
    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column game_reward.user_item_log_id
     *
     * @return the value of game_reward.user_item_log_id
     *
     * @mbggenerated
     */
    public Long getUserItemLogId() {
        return userItemLogId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column game_reward.user_item_log_id
     *
     * @param userItemLogId the value for game_reward.user_item_log_id
     *
     * @mbggenerated
     */
    public void setUserItemLogId(Long userItemLogId) {
        this.userItemLogId = userItemLogId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column game_reward.user_property_log_id
     *
     * @return the value of game_reward.user_property_log_id
     *
     * @mbggenerated
     */
    public Long getUserPropertyLogId() {
        return userPropertyLogId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column game_reward.user_property_log_id
     *
     * @param userPropertyLogId the value for game_reward.user_property_log_id
     *
     * @mbggenerated
     */
    public void setUserPropertyLogId(Long userPropertyLogId) {
        this.userPropertyLogId = userPropertyLogId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column game_reward.create_time
     *
     * @return the value of game_reward.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column game_reward.create_time
     *
     * @param createTime the value for game_reward.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column game_reward.rank
     *
     * @return the value of game_reward.rank
     *
     * @mbggenerated
     */
    public Integer getRank() {
        return rank;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column game_reward.rank
     *
     * @param rank the value for game_reward.rank
     *
     * @mbggenerated
     */
    public void setRank(Integer rank) {
        this.rank = rank;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column game_reward.team_id
     *
     * @return the value of game_reward.team_id
     *
     * @mbggenerated
     */
    public Long getTeamId() {
        return teamId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column game_reward.team_id
     *
     * @param teamId the value for game_reward.team_id
     *
     * @mbggenerated
     */
    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }
}