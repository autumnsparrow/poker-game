package com.sky.game.service.domain;

import java.util.Date;

public class UserPropertiesLog {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_properties_log.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_properties_log.user_account_id
     *
     * @mbggenerated
     */
    private Long userAccountId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_properties_log.property_id
     *
     * @mbggenerated
     */
    private Long propertyId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_properties_log.property_value
     *
     * @mbggenerated
     */
    private String propertyValue;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_properties_log.current_property_value
     *
     * @mbggenerated
     */
    private String currentPropertyValue;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_properties_log.update_time
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_properties_log.log_type
     *
     * @mbggenerated
     */
    private String logType;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_properties_log.id
     *
     * @return the value of user_properties_log.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_properties_log.id
     *
     * @param id the value for user_properties_log.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_properties_log.user_account_id
     *
     * @return the value of user_properties_log.user_account_id
     *
     * @mbggenerated
     */
    public Long getUserAccountId() {
        return userAccountId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_properties_log.user_account_id
     *
     * @param userAccountId the value for user_properties_log.user_account_id
     *
     * @mbggenerated
     */
    public void setUserAccountId(Long userAccountId) {
        this.userAccountId = userAccountId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_properties_log.property_id
     *
     * @return the value of user_properties_log.property_id
     *
     * @mbggenerated
     */
    public Long getPropertyId() {
        return propertyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_properties_log.property_id
     *
     * @param propertyId the value for user_properties_log.property_id
     *
     * @mbggenerated
     */
    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_properties_log.property_value
     *
     * @return the value of user_properties_log.property_value
     *
     * @mbggenerated
     */
    public String getPropertyValue() {
        return propertyValue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_properties_log.property_value
     *
     * @param propertyValue the value for user_properties_log.property_value
     *
     * @mbggenerated
     */
    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue == null ? null : propertyValue.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_properties_log.current_property_value
     *
     * @return the value of user_properties_log.current_property_value
     *
     * @mbggenerated
     */
    public String getCurrentPropertyValue() {
        return currentPropertyValue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_properties_log.current_property_value
     *
     * @param currentPropertyValue the value for user_properties_log.current_property_value
     *
     * @mbggenerated
     */
    public void setCurrentPropertyValue(String currentPropertyValue) {
        this.currentPropertyValue = currentPropertyValue == null ? null : currentPropertyValue.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_properties_log.update_time
     *
     * @return the value of user_properties_log.update_time
     *
     * @mbggenerated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_properties_log.update_time
     *
     * @param updateTime the value for user_properties_log.update_time
     *
     * @mbggenerated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_properties_log.log_type
     *
     * @return the value of user_properties_log.log_type
     *
     * @mbggenerated
     */
    public String getLogType() {
        return logType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_properties_log.log_type
     *
     * @param logType the value for user_properties_log.log_type
     *
     * @mbggenerated
     */
    public void setLogType(String logType) {
        this.logType = logType == null ? null : logType.trim();
    }
}