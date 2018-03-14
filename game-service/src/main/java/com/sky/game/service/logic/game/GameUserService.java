/**
 * sparrow
 * game-service 
 * Jan 10, 2015- 1:42:26 PM
 * @copyright BeiJing BoZhiWanTong Technonolgy Co.. Ltd .
 *
 */
package com.sky.game.service.logic.game;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sky.game.context.util.GameUtil;
import com.sky.game.service.domain.GameUser;
import com.sky.game.service.domain.ItemTypes;
import com.sky.game.service.domain.PropertyTypes;
import com.sky.game.service.domain.UserAccount;
import com.sky.game.service.domain.UserExtra;
import com.sky.game.service.domain.UserItems;
import com.sky.game.service.domain.UserLevel;
import com.sky.game.service.persistence.UserAccountMapper;
import com.sky.game.service.persistence.UserLevelMapper;

/**
 * @author sparrow
 *
 */
@Service
public class GameUserService {

	@Autowired
	UserAccountMapper userAccountMapper;

	@Autowired
	UserLevelMapper userLevelMapper;

	/**
	 * 
	 */
	public GameUserService() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * get user level by exp
	 * 
	 * @param exp
	 * @return
	 */
	private UserLevel getUserLevelByExp(int exp) {
		Map<Integer, UserLevel> levels = getUserLevelAsMap();
		Integer[] expes = levels.keySet().toArray(new Integer[] {});

		final boolean asc = true;
		Arrays.sort(expes, new Comparator<Integer>() {

			public int compare(Integer o1, Integer o2) {
				// TODO Auto-generated method stub
				return asc ? (o2.intValue() - o1.intValue())
						: (o1.intValue() - o2.intValue());
			}

		});
		Integer level = Integer.valueOf(0);
		for (int i = 0; i < expes.length; i++) {
			level = expes[i];
			UserLevel l = levels.get(level);
			if (l != null && exp > l.getExp()) {
				break;
			}
		}

		UserLevel l = levels.get(level);

		return l;

	}

	/**
	 * get user level as map.
	 * 
	 * @return
	 */
	private Map<Integer, UserLevel> getUserLevelAsMap() {
		List<UserLevel> objs = userLevelMapper.selectUserLevel();
		Map<Integer, UserLevel> levels = GameUtil.getMap();
		for (UserLevel o : objs) {
			levels.put(o.getLevelValue(), o);
		}
		return levels;
	}

	/**
	 * wrap useraccount to GameUser
	 * 
	 * @param account
	 * @return
	 */
	private GameUser wrap(UserAccount account) {

		GameUser o = GameUtil.obtain(GameUser.class);//
		if (account != null) {
			o.setUserId(account.getId());
			o.setChannelId(account.getChannelId());
			//
			// get coin and gold
			Map<ItemTypes, UserItems> items = account.getItemsAsMap();
			UserItems item = items.get(ItemTypes.Coin);
			if (item != null) {
				o.setCoin(item.getItemValue());
			}

			item = items.get(ItemTypes.Gold);

			if (item != null) {
				o.setGold(item.getItemValue());
			}

			// get exp and level
			Map<PropertyTypes, UserExtra> properties = account
					.getPropertiesAsMap();

			UserExtra property = properties.get(PropertyTypes.Experience);
			if (property != null) {
				o.setExp(GameUtil.s2i(property.getPropertyValue()));
			}

			property = properties.get(PropertyTypes.NickName);
			if (property != null) {
				o.setNickName(property.getPropertyValue());
			}

			// level
			UserLevel level = getUserLevelByExp(o.getExp());
			if (level != null) {
				o.setLevel(level.getLevelValue().intValue());
				o.setLevelName(level.getLevelName());
			}

			// icon
			property = properties.get(PropertyTypes.StartHead);
			if (property != null) {
				o.setIcon(property.getPropertyValue());
			}
			
		}
		return o;

	}

	/**
	 * 
	 * export this method to protocol
	 * 
	 * @param userId
	 * @return
	 */
	public GameUser getGameUser(Long userId) {
		UserAccount account = userAccountMapper.selectByPrimaryId(userId);

		return wrap(account);
	}

	/**
	 * 
	 * 
	 * @param userIds
	 * @return
	 */
	public List<GameUser> getGameUser(List<Long> userIds) {
		List<GameUser> accounts = null;
		accounts = GameUtil.getList();
		for (Long id : userIds) {
			UserAccount account = userAccountMapper.selectByPrimaryId(id);
			accounts.add(wrap(account));
		}

		return accounts;

	}

}
