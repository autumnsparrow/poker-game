package com.sky.game.service.persistence;

import java.util.HashMap;
import java.util.List;

import com.sky.game.service.domain.UserAchievement;

public interface UserAchievementMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_achievement
     *
     * @mbggenerated
     */
    int insert(UserAchievement record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_achievement
     *
     * @mbggenerated
     */
    int insertSelective(UserAchievement record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_achievement
     *
     * @mbggenerated
     */
    UserAchievement selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_achievement
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(UserAchievement record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_achievement
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(UserAchievement record);
    /*
     * 查询用户成就列表
     */
    List<UserAchievement> selectAll(long userId);
    /*
     * 修改用户成就状态
     */
    int updateUserAchievementState(HashMap<String,Object> hashmap);
    /*
     * 根据用户id 和type类型 查询userAchievement列表
     */
    List<UserAchievement> selectByUseridAndType(HashMap<String,Object> hashmap);
    /**
     * 根据用户id 和systemAchievementId 根询  地主和农民 胜
     */
    List<UserAchievement> selectLoardorNm (HashMap<String,Object> hashmap);
    List<UserAchievement> selectDorN (HashMap<String,Object> hashmap);
}