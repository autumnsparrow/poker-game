package com.sky.game.service.persistence;

import java.util.List;

import com.sky.game.service.domain.GameBlockadeMessageMap;

public interface GameBlockadeMessageMapMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table game_blockade_message_map
     *
     * @mbggenerated
     */
    int insert(GameBlockadeMessageMap record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table game_blockade_message_map
     *
     * @mbggenerated
     */
    int insertSelective(GameBlockadeMessageMap record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table game_blockade_message_map
     *
     * @mbggenerated
     */
    GameBlockadeMessageMap selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table game_blockade_message_map
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(GameBlockadeMessageMap record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table game_blockade_message_map
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(GameBlockadeMessageMap record);
    
    
    List<GameBlockadeMessageMap> selectByToUserId(Long id);
    
    int updateStateByPrimaryKey(GameBlockadeMessageMap record);
    
    Long selectUnreadMessageCount(Long gbuId);
}