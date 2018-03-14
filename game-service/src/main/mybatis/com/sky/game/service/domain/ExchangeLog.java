package com.sky.game.service.domain;

import java.util.Date;

public class ExchangeLog {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column exchange_trade_log.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column exchange_trade_log.exchange_id
     *
     * @mbggenerated
     */
    private Long exchangeId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column exchange_trade_log.user_account_id
     *
     * @mbggenerated
     */
    private Long userAccountId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column exchange_trade_log.total_count
     *
     * @mbggenerated
     */
    private Long totalCount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column exchange_trade_log.count
     *
     * @mbggenerated
     */
    private Long count;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column exchange_trade_log.update_time
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column exchange_trade_log.id
     *
     * @return the value of exchange_trade_log.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column exchange_trade_log.id
     *
     * @param id the value for exchange_trade_log.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column exchange_trade_log.exchange_id
     *
     * @return the value of exchange_trade_log.exchange_id
     *
     * @mbggenerated
     */
    public Long getExchangeId() {
        return exchangeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column exchange_trade_log.exchange_id
     *
     * @param exchangeId the value for exchange_trade_log.exchange_id
     *
     * @mbggenerated
     */
    public void setExchangeId(Long exchangeId) {
        this.exchangeId = exchangeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column exchange_trade_log.user_account_id
     *
     * @return the value of exchange_trade_log.user_account_id
     *
     * @mbggenerated
     */
    public Long getUserAccountId() {
        return userAccountId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column exchange_trade_log.user_account_id
     *
     * @param userAccountId the value for exchange_trade_log.user_account_id
     *
     * @mbggenerated
     */
    public void setUserAccountId(Long userAccountId) {
        this.userAccountId = userAccountId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column exchange_trade_log.total_count
     *
     * @return the value of exchange_trade_log.total_count
     *
     * @mbggenerated
     */
    public Long getTotalCount() {
        return totalCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column exchange_trade_log.total_count
     *
     * @param totalCount the value for exchange_trade_log.total_count
     *
     * @mbggenerated
     */
    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column exchange_trade_log.count
     *
     * @return the value of exchange_trade_log.count
     *
     * @mbggenerated
     */
    public Long getCount() {
        return count;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column exchange_trade_log.count
     *
     * @param count the value for exchange_trade_log.count
     *
     * @mbggenerated
     */
    public void setCount(Long count) {
        this.count = count;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column exchange_trade_log.update_time
     *
     * @return the value of exchange_trade_log.update_time
     *
     * @mbggenerated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column exchange_trade_log.update_time
     *
     * @param updateTime the value for exchange_trade_log.update_time
     *
     * @mbggenerated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}