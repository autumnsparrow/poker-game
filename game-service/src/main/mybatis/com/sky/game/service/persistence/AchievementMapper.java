package com.sky.game.service.persistence;

import com.sky.game.service.domain.Achievement;

public interface AchievementMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table achievement
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table achievement
     *
     * @mbggenerated
     */
    int insert(Achievement record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table achievement
     *
     * @mbggenerated
     */
    int insertSelective(Achievement record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table achievement
     *
     * @mbggenerated
     */
    Achievement selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table achievement
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Achievement record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table achievement
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Achievement record);
}