package com.sky.game.service.domain;

public class SystemChargeCard {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column system_charge_card.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column system_charge_card.charge_card_attribute_id
     *
     * @mbggenerated
     */
    private Long chargeCardAttributeId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column system_charge_card.card_password
     *
     * @mbggenerated
     */
    private String cardPassword;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column system_charge_card.user_account_id
     *
     * @mbggenerated
     */
    private Long userAccountId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column system_charge_card.state
     *
     * @mbggenerated
     */
    private Integer state;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column system_charge_card.id
     *
     * @return the value of system_charge_card.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column system_charge_card.id
     *
     * @param id the value for system_charge_card.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column system_charge_card.charge_card_attribute_id
     *
     * @return the value of system_charge_card.charge_card_attribute_id
     *
     * @mbggenerated
     */
    public Long getChargeCardAttributeId() {
        return chargeCardAttributeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column system_charge_card.charge_card_attribute_id
     *
     * @param chargeCardAttributeId the value for system_charge_card.charge_card_attribute_id
     *
     * @mbggenerated
     */
    public void setChargeCardAttributeId(Long chargeCardAttributeId) {
        this.chargeCardAttributeId = chargeCardAttributeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column system_charge_card.card_password
     *
     * @return the value of system_charge_card.card_password
     *
     * @mbggenerated
     */
    public String getCardPassword() {
        return cardPassword;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column system_charge_card.card_password
     *
     * @param cardPassword the value for system_charge_card.card_password
     *
     * @mbggenerated
     */
    public void setCardPassword(String cardPassword) {
        this.cardPassword = cardPassword == null ? null : cardPassword.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column system_charge_card.user_account_id
     *
     * @return the value of system_charge_card.user_account_id
     *
     * @mbggenerated
     */
    public Long getUserAccountId() {
        return userAccountId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column system_charge_card.user_account_id
     *
     * @param userAccountId the value for system_charge_card.user_account_id
     *
     * @mbggenerated
     */
    public void setUserAccountId(Long userAccountId) {
        this.userAccountId = userAccountId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column system_charge_card.state
     *
     * @return the value of system_charge_card.state
     *
     * @mbggenerated
     */
    public Integer getState() {
        return state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column system_charge_card.state
     *
     * @param state the value for system_charge_card.state
     *
     * @mbggenerated
     */
    public void setState(Integer state) {
        this.state = state;
    }
}