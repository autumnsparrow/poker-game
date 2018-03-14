package com.sky.game.service.persistence;

import java.util.HashMap;
import com.sky.game.service.domain.UserReport;

public interface UserReportMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_report
     *
     * @mbggenerated
     */
    int insert(UserReport record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_report
     *
     * @mbggenerated
     */
    int insertSelective(UserReport record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_report
     *
     * @mbggenerated
     */
    UserReport selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_report
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(UserReport record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_report
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(UserReport record);
    
    int selectContinucus(long userId);
    
    void updateLastTime(HashMap<String,Object> map);
    
   void updateContinucusDays(long userId);
   
   UserReport selectByUserId(long userId);
   
   void insertContinucusDays(HashMap<String,Object> map);
   
   void updateContinucusDays(HashMap<String,Object> map);
   
   void updateReportTimmer();
   
}