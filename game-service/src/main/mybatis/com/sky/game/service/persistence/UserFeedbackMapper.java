package com.sky.game.service.persistence;

import java.util.HashMap;

import com.sky.game.service.domain.UserFeedback;

public interface UserFeedbackMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_feedback
     *
     * @mbggenerated
     */
    int insert(UserFeedback record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_feedback
     *
     * @mbggenerated
     */
    int insertSelective(UserFeedback record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_feedback
     *
     * @mbggenerated
     */
    UserFeedback selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_feedback
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(UserFeedback record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_feedback
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(UserFeedback record);
    
    void insertFeedback(HashMap<String,Object> map);
    
}