package com.sky.game.service.persistence;

import java.util.HashMap;
import java.util.List;

import com.sky.game.service.domain.TaskState;
import com.sky.game.service.domain.UserTask;

public interface UserTaskMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_task
     *
     * @mbggenerated
     */
    int insert(UserTask record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_task
     *
     * @mbggenerated
     */
    int insertSelective(UserTask record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_task
     *
     * @mbggenerated
     */
    UserTask selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_task
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(UserTask record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_task
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(UserTask record);
    
    List<TaskState> selectUserRate1(long userId);
    
    List<TaskState> selectUserRate2(long userId);
    
    UserTask selectByUserId(HashMap<String,Object> map);
    
    void insertTaskRate(HashMap<String,Object> map);
    
    void updateTaskRate(HashMap<String,Object> map);
    
    void updateTimming();
    
    List<TaskState> selectUserRate3(long userId);
    
    List<UserTask> selectUserTask(long userId);
    

    
}