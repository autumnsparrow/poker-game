package com.sky.game.service.domain;

public class Reward {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column reward.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column reward.reward_value
     *
     * @mbggenerated
     */
    private Integer rewardValue;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column reward.reward_type
     *
     * @mbggenerated
     */
    private Integer rewardType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column reward.item_id
     *
     * @mbggenerated
     */
    private Long itemId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column reward.id
     *
     * @return the value of reward.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column reward.id
     *
     * @param id the value for reward.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column reward.reward_value
     *
     * @return the value of reward.reward_value
     *
     * @mbggenerated
     */
    public Integer getRewardValue() {
        return rewardValue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column reward.reward_value
     *
     * @param rewardValue the value for reward.reward_value
     *
     * @mbggenerated
     */
    public void setRewardValue(Integer rewardValue) {
        this.rewardValue = rewardValue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column reward.reward_type
     *
     * @return the value of reward.reward_type
     *
     * @mbggenerated
     */
    public Integer getRewardType() {
        return rewardType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column reward.reward_type
     *
     * @param rewardType the value for reward.reward_type
     *
     * @mbggenerated
     */
    public void setRewardType(Integer rewardType) {
        this.rewardType = rewardType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column reward.item_id
     *
     * @return the value of reward.item_id
     *
     * @mbggenerated
     */
    public Long getItemId() {
        return itemId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column reward.item_id
     *
     * @param itemId the value for reward.item_id
     *
     * @mbggenerated
     */
    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }
}