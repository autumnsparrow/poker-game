package com.sky.game.service.persistence;

import java.util.HashMap;
import java.util.List;

import com.sky.game.service.domain.Flag;

public interface FlagMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table flag
     *
     * @mbggenerated
     */
    int insert(Flag record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table flag
     *
     * @mbggenerated
     */
    int insertSelective(Flag record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table flag
     *
     * @mbggenerated
     */
    Flag selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table flag
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Flag record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table flag
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Flag record);
    
    
    int insertFlag(HashMap<String,Object> map);
    /*删除中转表中的数据*/
    void deleteFlagByUserId(long userId);
    
    List<Flag> selectAllFlag();
    
    List<Flag> countByUserId(long userId);
    
    Flag selectByUserId(long userId);
    
    List<Flag> selectByflagId(long flagId);
}