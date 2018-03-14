package com.sky.game.service.domain;

public class ChannelPayment {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column channel_payment.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column channel_payment.jf_id
     *
     * @mbggenerated
     */
    private Integer jfId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column channel_payment.item_name
     *
     * @mbggenerated
     */
    private String itemName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column channel_payment.item_description
     *
     * @mbggenerated
     */
    private String itemDescription;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column channel_payment.price
     *
     * @mbggenerated
     */
    private Integer price;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column channel_payment.payment_channel
     *
     * @mbggenerated
     */
    private Integer paymentChannel;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column channel_payment.store_id
     *
     * @mbggenerated
     */
    private Long storeId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column channel_payment.id
     *
     * @return the value of channel_payment.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column channel_payment.id
     *
     * @param id the value for channel_payment.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column channel_payment.jf_id
     *
     * @return the value of channel_payment.jf_id
     *
     * @mbggenerated
     */
    public Integer getJfId() {
        return jfId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column channel_payment.jf_id
     *
     * @param jfId the value for channel_payment.jf_id
     *
     * @mbggenerated
     */
    public void setJfId(Integer jfId) {
        this.jfId = jfId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column channel_payment.item_name
     *
     * @return the value of channel_payment.item_name
     *
     * @mbggenerated
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column channel_payment.item_name
     *
     * @param itemName the value for channel_payment.item_name
     *
     * @mbggenerated
     */
    public void setItemName(String itemName) {
        this.itemName = itemName == null ? null : itemName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column channel_payment.item_description
     *
     * @return the value of channel_payment.item_description
     *
     * @mbggenerated
     */
    public String getItemDescription() {
        return itemDescription;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column channel_payment.item_description
     *
     * @param itemDescription the value for channel_payment.item_description
     *
     * @mbggenerated
     */
    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription == null ? null : itemDescription.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column channel_payment.price
     *
     * @return the value of channel_payment.price
     *
     * @mbggenerated
     */
    public Integer getPrice() {
        return price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column channel_payment.price
     *
     * @param price the value for channel_payment.price
     *
     * @mbggenerated
     */
    public void setPrice(Integer price) {
        this.price = price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column channel_payment.payment_channel
     *
     * @return the value of channel_payment.payment_channel
     *
     * @mbggenerated
     */
    public Integer getPaymentChannel() {
        return paymentChannel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column channel_payment.payment_channel
     *
     * @param paymentChannel the value for channel_payment.payment_channel
     *
     * @mbggenerated
     */
    public void setPaymentChannel(Integer paymentChannel) {
        this.paymentChannel = paymentChannel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column channel_payment.store_id
     *
     * @return the value of channel_payment.store_id
     *
     * @mbggenerated
     */
    public Long getStoreId() {
        return storeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column channel_payment.store_id
     *
     * @param storeId the value for channel_payment.store_id
     *
     * @mbggenerated
     */
    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }
}