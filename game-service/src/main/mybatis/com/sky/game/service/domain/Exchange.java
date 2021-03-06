package com.sky.game.service.domain;

import java.util.Date;

public class Exchange {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column exchange.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column exchange.item_id
     *
     * @mbggenerated
     */
    private Long itemId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column exchange.effect_time
     *
     * @mbggenerated
     */
    private Date effectTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column exchange.expire_time
     *
     * @mbggenerated
     */
    private Date expireTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column exchange.total_count
     *
     * @mbggenerated
     */
    private Long totalCount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column exchange.from_item_id
     *
     * @mbggenerated
     */
    private Long fromItemId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column exchange.from_item_count
     *
     * @mbggenerated
     */
    private Long fromItemCount;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column exchange.id
     *
     * @return the value of exchange.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column exchange.id
     *
     * @param id the value for exchange.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column exchange.item_id
     *
     * @return the value of exchange.item_id
     *
     * @mbggenerated
     */
    public Long getItemId() {
        return itemId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column exchange.item_id
     *
     * @param itemId the value for exchange.item_id
     *
     * @mbggenerated
     */
    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column exchange.effect_time
     *
     * @return the value of exchange.effect_time
     *
     * @mbggenerated
     */
    public Date getEffectTime() {
        return effectTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column exchange.effect_time
     *
     * @param effectTime the value for exchange.effect_time
     *
     * @mbggenerated
     */
    public void setEffectTime(Date effectTime) {
        this.effectTime = effectTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column exchange.expire_time
     *
     * @return the value of exchange.expire_time
     *
     * @mbggenerated
     */
    public Date getExpireTime() {
        return expireTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column exchange.expire_time
     *
     * @param expireTime the value for exchange.expire_time
     *
     * @mbggenerated
     */
    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column exchange.total_count
     *
     * @return the value of exchange.total_count
     *
     * @mbggenerated
     */
    public Long getTotalCount() {
        return totalCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column exchange.total_count
     *
     * @param totalCount the value for exchange.total_count
     *
     * @mbggenerated
     */
    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column exchange.from_item_id
     *
     * @return the value of exchange.from_item_id
     *
     * @mbggenerated
     */
    public Long getFromItemId() {
        return fromItemId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column exchange.from_item_id
     *
     * @param fromItemId the value for exchange.from_item_id
     *
     * @mbggenerated
     */
    public void setFromItemId(Long fromItemId) {
        this.fromItemId = fromItemId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column exchange.from_item_count
     *
     * @return the value of exchange.from_item_count
     *
     * @mbggenerated
     */
    public Long getFromItemCount() {
        return fromItemCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column exchange.from_item_count
     *
     * @param fromItemCount the value for exchange.from_item_count
     *
     * @mbggenerated
     */
    public void setFromItemCount(Long fromItemCount) {
        this.fromItemCount = fromItemCount;
    }
}