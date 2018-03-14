package com.sky.game.service.domain;

import java.io.Serializable;

public class UserExtra implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 5556028566322999384L;

	/**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_extra.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_extra.user_account_id
     *
     * @mbggenerated
     */
    private Long userAccountId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_extra.property_id
     *
     * @mbggenerated
     */
    private Long propertyId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_extra.property_value
     *
     * @mbggenerated
     */
    private String propertyValue;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_extra.id
     *
     * @return the value of user_extra.id
     *
     * @mbggenerated
     */
    
    private Property property;
    
    public Property getProperty() {
		return property;
	}

	public void setProperty(Property property) {
		this.property = property;
	}

	public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_extra.id
     *
     * @param id the value for user_extra.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_extra.user_account_id
     *
     * @return the value of user_extra.user_account_id
     *
     * @mbggenerated
     */
    public Long getUserAccountId() {
        return userAccountId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_extra.user_account_id
     *
     * @param userAccountId the value for user_extra.user_account_id
     *
     * @mbggenerated
     */
    public void setUserAccountId(Long userAccountId) {
        this.userAccountId = userAccountId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_extra.property_id
     *
     * @return the value of user_extra.property_id
     *
     * @mbggenerated
     */
    public Long getPropertyId() {
        return propertyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_extra.property_id
     *
     * @param propertyId the value for user_extra.property_id
     *
     * @mbggenerated
     */
    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_extra.property_value
     *
     * @return the value of user_extra.property_value
     *
     * @mbggenerated
     */
    public String getPropertyValue() {
        return propertyValue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_extra.property_value
     *
     * @param propertyValue the value for user_extra.property_value
     *
     * @mbggenerated
     */
    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue == null ? null : propertyValue.trim();
    }
}