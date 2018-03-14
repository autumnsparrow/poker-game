package com.sky.game.service.logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.sky.game.service.domain.Certificate;
import com.sky.game.service.domain.Detail;
import com.sky.game.service.domain.GameReward;
import com.sky.game.service.domain.Head;
import com.sky.game.service.domain.Honor;
import com.sky.game.service.domain.Privilege;
import com.sky.game.service.domain.PrivilegeDetailed;
import com.sky.game.service.domain.PrivilegeShow;
import com.sky.game.service.domain.PropertyTypes;
import com.sky.game.service.domain.SystemHead;
import com.sky.game.service.domain.TuPianId;
import com.sky.game.service.domain.UserAccount;
import com.sky.game.service.domain.UserExtra;
import com.sky.game.service.domain.UserHead;
import com.sky.game.service.domain.UserHoner;
import com.sky.game.service.domain.UserItemLog;
import com.sky.game.service.domain.UserItems;
import com.sky.game.service.domain.UserVip;
import com.sky.game.service.logic.game.GameBlockadeMessageService;
import com.sky.game.service.persistence.GameRewardMapper;
import com.sky.game.service.persistence.ItemMapper;
import com.sky.game.service.persistence.MatchCheckMapper;
import com.sky.game.service.persistence.PrivilegeDetailedMapper;
import com.sky.game.service.persistence.PrivilegeMapper;
import com.sky.game.service.persistence.RewardMapper;
import com.sky.game.service.persistence.SystemHeadMapper;
import com.sky.game.service.persistence.UserAccountMapper;
import com.sky.game.service.persistence.UserBankMapper;
import com.sky.game.service.persistence.UserExtraMapper;
import com.sky.game.service.persistence.UserItemLogMapper;
import com.sky.game.service.persistence.UserItemsMapper;
import com.sky.game.service.persistence.UserLevelMapper;
import com.sky.game.service.persistence.UserRewardMapper;
import com.sky.game.service.persistence.UserVipMapper;

/**
 * @author Administrator
 *
 */
@Service
public class UserCenterService {

	private static final Log logger = LogFactory
			.getLog(UserCenterService.class);

	@Autowired
	UserAccountMapper userAccountMapper;

	@Autowired
	UserExtraMapper userExtraMapper;

	@Autowired
	UserItemsMapper userItemsMapper;

	@Autowired
	UserRewardMapper userRewardMapper;

	@Autowired
	ItemMapper itemMapper;

	@Autowired
	PrivilegeMapper privilegeMapper;

	@Autowired
	UserItemLogMapper userItemLogMapper;

	@Autowired
	UserBankMapper userBankMapper;
	@Autowired
	MatchCheckMapper matchCheckMapper;

	@Autowired
	RewardMapper rewardMapper;
	@Autowired
	SystemHeadMapper systemHeadMapper;

	@Autowired
	UserLevelMapper userLevelMapper;
	/*
	 * @Autowired SequenceService sequenceService;
	 */
	@Autowired
	BillboardService billboardService;
	@Autowired
	GameRewardMapper gameRewardMapper;

	@Autowired
	UserVipMapper userVipMapper;
	@Autowired
	PrivilegeDetailedMapper privilegeDetailedMapper;
	@Autowired
	BadWordService badWordService;

	public UserCenterService() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * 修改昵称
	 */
	@Transactional
	public int updateNickName(long userId, String NickName) {
		int state = 1;
		UserAccount userAccount = userAccountMapper.selectByPrimaryId(userId);
		Map<PropertyTypes, UserExtra> mapExtra = userAccount
				.getPropertiesAsMap();
		UserItems userItems = userItemsMapper.selectByUserId(userId);
		UserExtra userExtra = mapExtra.get(PropertyTypes.Vip);
		String sss = mapExtra.get(PropertyTypes.Vip).getPropertyValue();
	    boolean bl = false;
		try {
			bl = badWordService.initfiltercode(NickName);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(bl!=true){
		if (mapExtra.get(PropertyTypes.Vip) != null
				&& "1".equals(mapExtra.get(PropertyTypes.Vip)
						.getPropertyValue())) {
			if (userItems.getItemValue() >= 500) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("userId", userId);
				map.put("count", 500);
				userItemsMapper.decUserCoin(map);
				// 修改昵称 日志 start
				UserItemLog userItemLog = new UserItemLog();
				// Long id=sequenceService.generateUserItemLogId();
				// userItemLog.setId(id);
				userItemLog.setItemId(userItems.getItemId());
				userItemLog.setValue(-500);
				userItemLog.setItemValue(userItems.getItemValue());
				userItemLog.setResumType("修改昵称");
				userItemLog.setUserAccountId(userId);
				userItemLog.setUpdateTime(new Date());
				userItemLogMapper.insertSelective(userItemLog);
				// end
				map.put("NickName", NickName);
				userExtraMapper.updateNickName(map);
			} else {
				state = -1;
				String description = "金币不足";
			}
		} else {
			if (userItems.getItemValue() >= 1000) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("userId", userId);
				map.put("count", 1000);
				userItemsMapper.decUserCoin(map);
				// 修改昵称 日志 start
				UserItemLog userItemLog = new UserItemLog();
				// Long id=sequenceService.generateUserItemLogId();
				// userItemLog.setId(id);
				userItemLog.setItemId(userItems.getItemId());
				userItemLog.setValue(-1000);
				userItemLog.setItemValue(userItems.getItemValue());
				userItemLog.setResumType("修改昵称");
				userItemLog.setUserAccountId(userId);
				userItemLog.setUpdateTime(new Date());
				userItemLogMapper.insertSelective(userItemLog);
				// end
				map.put("NickName", NickName);
				userExtraMapper.updateNickName(map);
			} else {
				String description = "金币不足";
				state = -1;
			}
		}
		}else{
			state=-2;
		}
		return state;

	}

	public void updateUserSign(long userId, String UserSign) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("UserSign", UserSign);
		userExtraMapper.updateUserSign(map);
	}

	public void updateUserPhone(long userId, String Phone) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("phone", Phone);
		userExtraMapper.updateUserPhone(map);

	}

	/**
	 * 个人中心 奖状墙
	 */
	public List<Certificate> selectCertificate(long userId,int page){
		List<Certificate> cerificateList=new ArrayList<Certificate>();
		Map<PropertyTypes, UserExtra>  userExtraMap=userAccountMapper.selectByPrimaryId(userId).getPropertiesAsMap();
		HashMap<String,Object> hashmap=new HashMap<String,Object>();
		hashmap.put("userId", userId);
		hashmap.put("page", page*6);
		List<GameReward> gameRewardList=gameRewardMapper.selectUserReward(hashmap);//查询用户比赛 名次
		for(GameReward gameReward:gameRewardList){
			Certificate certificate=new Certificate();
			if(gameReward.getRoomId()==2000){
				certificate.setMatchName("免费新手赛");
			}
			if (gameReward.getRoomId() == 2001) {
				certificate.setMatchName("100金币赚金赛");
			}
			if (gameReward.getRoomId() == 2002) {
				certificate.setMatchName("5元充值卡大奖赛");
			}
			if (gameReward.getRoomId() == 2003) {
				certificate.setMatchName("1000金币夺宝赛");
			}
			if (gameReward.getRoomId() == 2004) {
				certificate.setMatchName("午间10元话费场");
			}
			if (gameReward.getRoomId() == 2005) {
				certificate.setMatchName("天天赢话费");
			}
			if (gameReward.getRoomId() == 2006) {
				certificate.setMatchName("周末赢大奖");
			}
			if (gameReward.getRoomId() == 3000) {
				certificate.setMatchName("VIP免费赢大奖");
			}
			if (gameReward.getRoomId() == 3001) {
				certificate.setMatchName("VIP2000金币PK赛");
			}
			if (gameReward.getRoomId() == 3002) {
				certificate.setMatchName("VIP5000金币PK赛");
			}
			if (gameReward.getRoomId() == 3003) {
				certificate.setMatchName("VIP10000金币PK赛");
			}
			if (gameReward.getRoomId() == 3004) {
				certificate.setMatchName("VIP10000金币夺宝赛");
			}
			if (userExtraMap.get(PropertyTypes.NickName) != null) {
				certificate.setNickName(userExtraMap
						.get(PropertyTypes.NickName).getPropertyValue());
			}
			certificate.setRank(String.valueOf(gameReward.getRank()));
			certificate.setTime(gameReward.getCreateTime());
			UserItemLog userItemLog = userItemLogMapper
					.selectByPrimaryKey(gameReward.getUserItemLogId());
			String itemName = itemMapper.selectByPrimaryKey(
					userItemLog.getItemId()).getName();
			int itemValue = userItemLog.getValue();
			certificate.setReWardArray(new String[] { itemName + "x"
					+ itemValue });
			cerificateList.add(certificate);
		}
		return cerificateList;

	}

	/*
	 * 个人中心 我的荣誉
	 */
	public Honor userHonor(long userId) {
		Map<PropertyTypes, UserExtra> map = userAccountMapper
				.selectByPrimaryId(userId).getPropertiesAsMap();
		UserHoner userHoner = new UserHoner();
		if (map.get(PropertyTypes.ReputationValue) != null) {
			userHoner.setCreditValue(map.get(PropertyTypes.ReputationValue)
					.getPropertyValue());
		} else {
			userHoner.setCreditValue("0");
		}
		if (map.get(PropertyTypes.BestBet) != null) {
			userHoner.setBestBet(map.get(PropertyTypes.BestBet)
					.getPropertyValue());
		} else {
			userHoner.setBestBet("0");
		}
		if (map.get(PropertyTypes.DsFen) != null) {
			userHoner.setDsFen(map.get(PropertyTypes.DsFen).getPropertyValue());
		} else {
			userHoner.setDsFen("0");
		}
		if (map.get(PropertyTypes.MaxGot) != null) {
			userHoner.setMaxGot(map.get(PropertyTypes.MaxGot)
					.getPropertyValue());
		} else {
			userHoner.setMaxGot("0");
		}
		if (map.get(PropertyTypes.MlValue) != null) {
			userHoner.setMlValue(map.get(PropertyTypes.MlValue)
					.getPropertyValue());
		} else {
			userHoner.setMlValue("0");
		}
		if (map.get(PropertyTypes.RpValue) != null) {
			userHoner.setRpValue(map.get(PropertyTypes.RpValue)
					.getPropertyValue());
		} else {
			userHoner.setRpValue("0");
		}
		if (map.get(PropertyTypes.TtFen) != null) {
			userHoner.setTtFen(map.get(PropertyTypes.TtFen).getPropertyValue());
		} else {
			userHoner.setTtFen("0");
		}
		// map.get()
		// 战报
		Honor honor = new Honor();
		honor.setUserHoner(userHoner);

		honor.setRecord(billboardService.selectWarReport(userId));
		return honor;
	}

	/*
	 * 
	 * 个人中心 我的特权
	 * 
	 * @see
	 * com.sky.game.service.logic.UserCenterService.userAccountMapper#userPrivilege
	 * (long userId)
	 */
	public PrivilegeShow userPrivilege(long userId) {
		PrivilegeShow privilegeShow = new PrivilegeShow();
		UserExtra u = userAccountMapper.selectByPrimaryId(userId)
				.getPropertiesAsMap().get(PropertyTypes.Vip);
		UserVip userVip = userVipMapper.selectVipOn(userId);
		int level = 0;
		if (userVip != null) {
			level = userVip.getVipLevel();
			privilegeShow.setVipLevel("lv." + level);
			Date d = userVip.getVipEndTime();
			privilegeShow.setLoseTime(d);
			int vipFen = userVip.getVipPoint();
			privilegeShow.setVipFen(vipFen);
		}
		List<Privilege> privilegeList = null;
		if (u == null) {
			PrivilegeDetailed pri = privilegeDetailedMapper
					.selectPrivilegeDetailedByLevel(level);
			privilegeList = privilegeMapper.selectAllPrivilege(1);
			privilegeList.get(0).setDiscount(null);
			privilegeList.get(1)
					.setDiscount(String.valueOf(pri.getFrendsMax()));
			privilegeList.get(2).setDiscount(
					String.valueOf(pri.getGoodsDiscount()));
			privilegeList.get(3).setDiscount(
					String.valueOf(pri.getLotteryCount()));
			privilegeList.get(4).setDiscount(String.valueOf(pri.getAddExp()));
			privilegeList.get(5).setDiscount(null);
		} else {
			if (u.getPropertyValue().equals("2")) {
				PrivilegeDetailed pri = privilegeDetailedMapper
						.selectPrivilegeDetailedByLevel(level);
				privilegeList = privilegeMapper.selectAllPrivilege(1);
				privilegeList.get(0).setDiscount(null);
				privilegeList.get(1).setDiscount(
						String.valueOf(pri.getFrendsMax()));
				privilegeList.get(2).setDiscount(
						String.valueOf(pri.getGoodsDiscount()));
				privilegeList.get(3).setDiscount(
						String.valueOf(pri.getLotteryCount()));
				privilegeList.get(4).setDiscount(
						String.valueOf(pri.getAddExp()));
				privilegeList.get(5).setDiscount(null);
			} else {
				PrivilegeDetailed pri = privilegeDetailedMapper
						.selectPrivilegeDetailedByLevel(level);
				privilegeList = privilegeMapper.selectAllPrivilege(2);
				privilegeList.get(0).setDiscount(null);
				privilegeList.get(1).setDiscount(
						String.valueOf(pri.getFrendsMax()));
				privilegeList.get(2).setDiscount(
						String.valueOf(pri.getGoodsDiscount()));
				privilegeList.get(3).setDiscount(
						String.valueOf(pri.getLotteryCount()));
				privilegeList.get(4).setDiscount(
						String.valueOf(pri.getAddExp()));
				privilegeList.get(5).setDiscount(null);
			}
		}
		privilegeShow.setPrivilegeList(privilegeList);
		// privilegeShow.setFlag(flag);
		return privilegeShow;
	}

	/*
	 * 个人中心账号查询
	 */
	public List<Detail> selectUserCheck(long userId, int page) {
		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		hashmap.put("userId", userId);
		hashmap.put("startPage", page * 6);
		hashmap.put("endPage", 6);
		return userItemLogMapper.selectUserCheck(hashmap);
	}

	/*
	 * 修改登录密码
	 */
	public int userUpdatePassword(HashMap<String, Object> hashmap) {
		int i = userAccountMapper.updateUserPassword(hashmap);
		return i;
	}

	/*
	 * 个人中心修改头像
	 */
	public UserHead updaeHead(long userId) {
		UserHead userHead = new UserHead();
		List<TuPianId> tuPianIdList = userItemsMapper.selectTPI(userId);
		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		hashmap.put("itemType", 6);
		List<Head> vipHeadList = itemMapper.selectItemListByItemType(hashmap);
		if (vipHeadList != null) {
			for (int i = 0; i < vipHeadList.size(); i++) {
				if (tuPianIdList != null) {
					for (int j = 0; j < tuPianIdList.size(); j++) {
						if (vipHeadList.get(i).getIconId() == tuPianIdList.get(
								j).getIconId()) {
							vipHeadList.get(i).setIsPurchase(1);// 1 表示已购买
							break;
						} else {
							vipHeadList.get(i).setIsPurchase(2);// 2 表示未购买
						}
					}
				} else {
					vipHeadList.get(i).setIsPurchase(2);// 2 è¡¨ç¤ºæ²¡æè´­ä¹°
				}
			}
		}
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("itemType", 7);
		List<Head> CommonList = itemMapper.selectItemListByItemType(map);
		if (CommonList != null) {
			for (int i = 0; i < CommonList.size(); i++) {
				if (tuPianIdList != null) {
					for (int j = 0; j < tuPianIdList.size(); j++) {
						if (CommonList.get(i).getIconId() == tuPianIdList
								.get(j).getIconId()) {
							CommonList.get(i).setIsPurchase(1);// 表示已购买
							break;
						} else {
							CommonList.get(i).setIsPurchase(2);// 2 表示未购买
						}
					}
				} else {
					CommonList.get(i).setIsPurchase(2);// 2 è¡¨ç¤ºæ²¡æè´­ä¹°
				}
			}
		}
		userHead.setVipHeadList(vipHeadList);
		userHead.setCommonList(CommonList);
		return userHead;
	}

	/*
	 * 个人中心头像购买
	 */

	public int purchaseHead(long userId, long id) {
		int state = 0;
		int flag = 0;
		Map<PropertyTypes, UserExtra> map = userAccountMapper
				.selectByPrimaryId(userId).getPropertiesAsMap();
		SystemHead systemHead = systemHeadMapper.selectByPrimaryKey(id);
		if (map.get(PropertyTypes.Vip) != null) {
			if (map.get(PropertyTypes.Vip).getPropertyValue().equals("1")) {

				UserItems userItems = userItemsMapper.selectByUserId(userId);
				if (systemHead.getCount() <= userItems.getItemValue()) {
					HashMap<String, Object> hashmap = new HashMap<String, Object>();
					hashmap.put("userId", userId);
					hashmap.put("itemId", systemHead.getItemId());
					state = userItemsMapper.insertUserHead(hashmap);

					if (state < 0) {
						flag = -1;// è´­ä¹°å¤±è´¥sqléè¯¯
					}
					// String
					// descript=userItemsMapper.selectDescriptById(itemId);
					hashmap.put("value", systemHead.getCount());
					state = userItemsMapper.updateUserCoinNum(hashmap);
					// 添加购买头像 日志 start
					UserItemLog userItemLog = new UserItemLog();
					// Long ids=sequenceService.generateUserItemLogId();
					// userItemLog.setId(ids);
					userItemLog.setItemId(userItems.getItemId());
					userItemLog.setValue(-systemHead.getCount());
					userItemLog.setItemValue(userItems.getItemValue());
					userItemLog.setResumType("购买头像");
					userItemLog.setUserAccountId(userId);
					userItemLog.setUpdateTime(new Date());
					userItemLogMapper.insertSelective(userItemLog);
					// end
					if (state < 0) {
						flag = -1;
					} else {
						flag = 1;
					}
				} else {
					flag = -2;// éå¸ä¸è¶³
				}
				// flag= 1;
			} else {
				int itemType = itemMapper.selectItemByItemId(systemHead
						.getItemId());
				if (itemType == 7) {
					// if(item.getItemType()==7){
					UserItems userItems = userItemsMapper
							.selectByUserId(userId);
					if (systemHead.getCount() <= userItems.getItemValue()) {
						HashMap<String, Object> hashmap = new HashMap<String, Object>();
						hashmap.put("userId", userId);
						hashmap.put("itemId", systemHead.getItemId());
						state = userItemsMapper.insertUserHead(hashmap);
						if (state < 0) {
							flag = -1;// è´­ä¹°å¤±è´¥sqléè¯¯
						} else {
							flag = 1;
						}
						// String
						// descript=userItemsMapper.selectDescriptById(itemId);
						hashmap.put("value", systemHead.getCount());
						state = userItemsMapper.updateUserCoinNum(hashmap);
						// 添加购买头像 日志 start
						UserItemLog userItemLog = new UserItemLog();
						// Long ids=sequenceService.generateUserItemLogId();
						// userItemLog.setId(ids);
						userItemLog.setItemId(userItems.getItemId());
						userItemLog.setValue(-systemHead.getCount());
						userItemLog.setItemValue(userItems.getItemValue());
						userItemLog.setResumType("购买头像");
						userItemLog.setUserAccountId(userId);
						userItemLog.setUpdateTime(new Date());
						userItemLogMapper.insertSelective(userItemLog);
						// end
						if (state < 0) {
							flag = -1;
						} else {
							flag = 1;
						}
					} else {
						flag = -2;// éå¸ä¸è¶³
					}
					// flag= 1;
				} else {
					flag = -4;// vip å¤±æ
				}
			}
		} else {
			int itemType = itemMapper
					.selectItemByItemId(systemHead.getItemId());
			if (itemType == 7) {
				UserItems userItems = userItemsMapper.selectByUserId(userId);
				if (systemHead.getCount() <= userItems.getItemValue()) {
					HashMap<String, Object> hashmap = new HashMap<String, Object>();
					hashmap.put("userId", userId);
					hashmap.put("itemId", systemHead.getItemId());
					state = userItemsMapper.insertUserHead(hashmap);
					if (state < 0) {
						flag = -1;// è´­ä¹°å¤±è´¥sqléè¯¯
					} else {
						flag = 1;
					}
					// String
					// descript=userItemsMapper.selectDescriptById(itemId);
					hashmap.put("value", systemHead.getCount());
					state = userItemsMapper.updateUserCoinNum(hashmap);
					// 添加购买头像 日志 start
					UserItemLog userItemLog = new UserItemLog();
					// Long ids=sequenceService.generateUserItemLogId();
					// userItemLog.setId(ids);
					userItemLog.setItemId(userItems.getItemId());
					userItemLog.setValue(-systemHead.getCount());
					userItemLog.setItemValue(userItems.getItemValue());
					userItemLog.setResumType("购买头像");
					userItemLog.setUserAccountId(userId);
					userItemLog.setUpdateTime(new Date());
					userItemLogMapper.insertSelective(userItemLog);
					// end
					if (state < 0) {
						flag = -1;
					} else {
						flag = 1;
					}
				} else {
					flag = -2;// éå¸ä¸è¶³
				}
				// flag= 1;//é®é¢ä»£ç 5555
			} else {
				flag = -4;// ä¸æ¯vipç¨æ·
			}
		}
		return flag;
	}

	/*
	 * æ ¹æ®è´¦å· id æ¥è¯¢ç¨æ·è´¦å·ä¿¡æ¯
	 */
	public UserAccount selectUserAccountByUserId(long userId) {
		UserAccount userAccount = userAccountMapper.selectByPrimaryId(userId);
		return userAccount;
	}

	/*
	 * æ ¹æ®è´¦å·id æ¥è¯¢ç¨æ·é¶è¡å¯ç 
	 */
	public String selectUserBankPasswordByUserId(long userId) {
		String bankPassword = userBankMapper.selectBankPw(userId);
		return bankPassword;
	}

	/*
	 * ä¿®æ¹ææå¥ç¨æ·ææºå·ç 
	 */
	public int insertOrUpdatePhone(long userId, String phone) {
		UserAccount userAccount = userAccountMapper.selectByPrimaryId(userId);
		UserExtra u = userAccount.getPropertiesAsMap().get(PropertyTypes.Phone);
		if (u == null) {
			UserExtra record = new UserExtra();
			record.setUserAccountId(userId);
			record.setPropertyValue(phone);
			record.setPropertyId(12L);
			userExtraMapper.insertSelective(record);
		}
		return 1;
	}

	/*
	 * 修改默认头像 保存
	 */
	public int updateDefaultHead(long userId, String url) {
		int state = -1;
		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		hashmap.put("userId", userId);
		hashmap.put("url", url);
		/*
		 * UserItems userItems=userItemsMapper.selectByUrl(hashmap);
		 * if(userItems!=null){
		 */
		state = userExtraMapper.updateUserStartHead(hashmap);
		/* } */
		return state;
	}
	/**
	 * 
	 * 修改用户属性 针对 大师分，天梯分，魅力值
	 */
	public int updateProperty(long userId,int fen,long propertyId){
		int state;
		HashMap<String,Object> hashmap=new HashMap<String,Object>();
		hashmap.put("propertyId",propertyId);
		hashmap.put("userId", userId);
		UserExtra ue=userExtraMapper.selectByUserIdAndProperty(hashmap);
		if(ue!=null){
			int v= fen+Integer.valueOf(ue.getPropertyValue());
			String value=String.valueOf(v);
			hashmap.put("propertyValue",value);
		  state=userExtraMapper.updateDTM(hashmap);
		}else{
			String value=String.valueOf(fen);
			hashmap.put("propertyValue", value);
			state=userExtraMapper.insertDTM(hashmap);
		}
		return state;
	}
}
