package com.sky.game.service.domain;

import java.util.Date;

public class GameBlockadeMessage {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column game_blockade_message.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column game_blockade_message.content
     *
     * @mbggenerated
     */
    private String content;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column game_blockade_message.create_time
     *
     * @mbggenerated
     */
    private Date createTime;
    
    
    private Long gameBlockadeMessageMapId;
    
    private GameUser gameUser;
    

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column game_blockade_message.id
     *
     * @return the value of game_blockade_message.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column game_blockade_message.id
     *
     * @param id the value for game_blockade_message.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column game_blockade_message.content
     *
     * @return the value of game_blockade_message.content
     *
     * @mbggenerated
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column game_blockade_message.content
     *
     * @param content the value for game_blockade_message.content
     *
     * @mbggenerated
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column game_blockade_message.create_time
     *
     * @return the value of game_blockade_message.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column game_blockade_message.create_time
     *
     * @param createTime the value for game_blockade_message.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public Long getGameBlockadeMessageMapId() {
		return gameBlockadeMessageMapId;
	}

	public void setGameBlockadeMessageMapId(Long gameBlockadeMessageMapId) {
		this.gameBlockadeMessageMapId = gameBlockadeMessageMapId;
	}

	public GameUser getGameUser() {
		return gameUser;
	}

	public void setGameUser(GameUser gameUser) {
		this.gameUser = gameUser;
	}
}