package com.sky.game.service.persistence;

import java.util.List;

import com.sky.game.service.domain.UserLevel;

public interface UserLevelMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_level
     *
     * @mbggenerated
     */
    int insert(UserLevel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_level
     *
     * @mbggenerated
     */
    int insertSelective(UserLevel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_level
     *
     * @mbggenerated
     */
    UserLevel selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_level
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(UserLevel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_level
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(UserLevel record);
    /*
     * 大厅用户信息展示 --级别
     */
    List<UserLevel> selectUserLevel();
    
    int selectCurrentLevel(int exp);
    
    String selectLevelName(int level);
    
}