package com.sky.game.service.persistence;

import com.sky.game.service.domain.PoinCardAttribute;

public interface PoinCardAttributeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table point_card_attribute
     *
     * @mbggenerated
     */
    int insert(PoinCardAttribute record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table point_card_attribute
     *
     * @mbggenerated
     */
    int insertSelective(PoinCardAttribute record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table point_card_attribute
     *
     * @mbggenerated
     */
    PoinCardAttribute selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table point_card_attribute
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(PoinCardAttribute record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table point_card_attribute
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(PoinCardAttribute record);
}