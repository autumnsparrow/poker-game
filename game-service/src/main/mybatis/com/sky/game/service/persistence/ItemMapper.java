package com.sky.game.service.persistence;

import java.util.HashMap;
import java.util.List;

import com.sky.game.service.domain.DefaultHead;
import com.sky.game.service.domain.Goods;
import com.sky.game.service.domain.Head;
import com.sky.game.service.domain.Item;

public interface ItemMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbggenerated
     */
    int insert(Item record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbggenerated
     */
    int insertSelective(Item record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbggenerated
     */
    Item selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Item record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Item record);
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbggenerated
     */
	  /* 
	   * 已经 改变到exchangeMapper.xml 
	   * List<Goods> selectItemGoods(long userId);
	   * */
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbggenerated
     */
   /* 
    * 已经 改变exchangMapper.xml
    * List<GoldExchange> selectItemGold();
    * */
    /**
     * 
     * 奖品对换 ，查询几天剩余数
     *
     */
    
    int selectResDay(long id);
    List<Head> selectItemListByItemType(HashMap<String,Object> hashmap);
    /*
     * 查询默认头像
     */
    List<DefaultHead> selectDefaultHeadList();
    
    List<Item> selectItems();
    /*
     *个人中心头像购买
     */
    int selectItemByItemId(long itemId);
    /*
     * 根据物品id查询物品名称
     */
    String selectItemNameByItemId(long itemId);
    
    List<Goods> selectItemGoods(long itemId);
    
    /**
     * select all Item in table items
     * <p>
     * @see Item
     * @see Icon
     * @return
     */
    List<Item> selectAllItems();
    
}