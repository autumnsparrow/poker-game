package com.sky.game.service.persistence;

import java.util.List;

import com.sky.game.service.domain.Reward;
import com.sky.game.service.domain.Task;
import com.sky.game.service.domain.TaskSet;

public interface TaskMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task
     *
     * @mbggenerated
     */
    int insert(Task record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task
     *
     * @mbggenerated
     */
    int insertSelective(Task record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task
     *
     * @mbggenerated
     */
    Task selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Task record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Task record);
    
    List<TaskSet> selectTaskSet1(long channelId);
    
    List<TaskSet> selectTaskSet2();
    
    long selectRewardId(long type);
    
    
    List<TaskSet> selectTaskSet3();
    
    List<Task> selectTask();
    
    
    
}