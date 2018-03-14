package com.sky.game.service.persistence;

import com.sky.game.service.domain.PrivilegeDetailed;

public interface PrivilegeDetailedMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table privilege_detailed
     *
     * @mbggenerated
     */
    int insert(PrivilegeDetailed record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table privilege_detailed
     *
     * @mbggenerated
     */
    int insertSelective(PrivilegeDetailed record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table privilege_detailed
     *
     * @mbggenerated
     */
    PrivilegeDetailed selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table privilege_detailed
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(PrivilegeDetailed record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table privilege_detailed
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(PrivilegeDetailed record);
    /**
     * 根据 vip level 查询PrivilegeDetailed
     * @see com.sky.game.service.persistence.PrivilegeDetailedMapper.xml#selectPrivilegeDetailedByLevel
     * @param level
     * @return
     */
    PrivilegeDetailed selectPrivilegeDetailedByLevel(int level);
}