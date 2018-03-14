package com.sky.game.service.persistence;

import java.util.HashMap;
import java.util.List;

import com.sky.game.service.domain.AuctionGoods;
import com.sky.game.service.domain.MyAuctionGoods;
import com.sky.game.service.domain.PoinCard;

public interface PoinCardMapper {
   /*
    * 根据id 查询AuctionGoods
    */
	PoinCard selectAuctionById(long id);
    
    /*
     * 查询所有拍卖物品
     */
    List<AuctionGoods> selectAuctionList();
    /*
     * 修改竞拍物品的当前价格
     */
    int updateNowPrice (HashMap<String,Object> hashmap);
    /*
     * 查询我的拍卖物品
     */
    List<MyAuctionGoods> selectMyAuctionList(long userId);

     /*
      * 我的拍卖行   上拍 
      */
     int updateMyAuction(HashMap<String,Object> hashmap);
     
     long selectByIdFindTypeId(long id);
     /*
      * 我的拍卖行流拍
      */
     int updateAuctionOutTime(long id);
     
     int updateAuctionGiveAuctioner(HashMap<String,Object> hashmap);
     
     /*
      * 拍买行  积分卡合成
      */
     int inserPointCard(PoinCard poinCard);
     /*
      * 积分卡限制
      */
     PoinCard selectUserIsOne(HashMap<String,Object> hashmap);
     /**
      * 删除积分卡
      */
     int deletePointCardById(long id);
     /*
      * 积分卡限制   没有拍买的积分卡才能使用
      */
     PoinCard selectxzUserUse(HashMap<String,Object> hashmap);
}