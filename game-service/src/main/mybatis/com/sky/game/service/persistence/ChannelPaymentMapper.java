package com.sky.game.service.persistence;

import com.sky.game.service.domain.ChannelPayment;

public interface ChannelPaymentMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table channel_payment
     *
     * @mbggenerated
     */
    int insert(ChannelPayment record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table channel_payment
     *
     * @mbggenerated
     */
    int insertSelective(ChannelPayment record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table channel_payment
     *
     * @mbggenerated
     */
    ChannelPayment selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table channel_payment
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(ChannelPayment record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table channel_payment
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(ChannelPayment record);
    
    ChannelPayment selectChannelPaymentByStoreId(long paramLong);
}