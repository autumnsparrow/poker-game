package com.sky.game.service.domain;

public class UserLottery {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_lottery.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_lottery.lottery_count
     *
     * @mbggenerated
     */
    private Integer lotteryCount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_lottery.user_account_id
     *
     * @mbggenerated
     */
    private Long userAccountId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_lottery.id
     *
     * @return the value of user_lottery.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_lottery.id
     *
     * @param id the value for user_lottery.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_lottery.lottery_count
     *
     * @return the value of user_lottery.lottery_count
     *
     * @mbggenerated
     */
    public Integer getLotteryCount() {
        return lotteryCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_lottery.lottery_count
     *
     * @param lotteryCount the value for user_lottery.lottery_count
     *
     * @mbggenerated
     */
    public void setLotteryCount(Integer lotteryCount) {
        this.lotteryCount = lotteryCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_lottery.user_account_id
     *
     * @return the value of user_lottery.user_account_id
     *
     * @mbggenerated
     */
    public Long getUserAccountId() {
        return userAccountId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_lottery.user_account_id
     *
     * @param userAccountId the value for user_lottery.user_account_id
     *
     * @mbggenerated
     */
    public void setUserAccountId(Long userAccountId) {
        this.userAccountId = userAccountId;
    }
    
}