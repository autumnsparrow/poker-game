/**
 * sparrow
 * game-service 
 * Dec 3, 2014- 10:59:48 AM
 * @copyright BeiJing BoZhiWanTong Technonolgy Co.. Ltd .
 *
 */
package com.sky.game.service.domain;

import java.io.Serializable;

/**
 * 
 * 
 * +----+-----------------+--------------------+---------+ | id | name |
 * description | icon_id |
 * +----+-----------------+--------------------+---------+ | 8 | Experience | 经验
 * | 5 | | 9 | UserSign | 个性签名 | 5 | | 10 | Address | 地址 | 5 | | 11 | PostCode |
 * 邮编 | 5 | | 12 | Phone | 电话号 | 5 | | 13 | Sex | 性别 | 5 | | 14 | DsFen | 大师积分 |
 * 5 | | 15 | TtFen | 天梯积分 | 5 | | 16 | RpValue | RP值 | 5 | | 17 | CreditValue |
 * 信誉值 | 5 | | 18 | MlValue | 魅力值 | 5 | | 19 | MaxGot | 最大获胜倍数 | 5 | | 20 |
 * BestBet | 最佳战绩 | 5 | | 21 | Vip | vip | 5 | | 22 | Country | Country | 5 | |
 * 23 | Province | Province | 5 | | 24 | City | City | 5 | | 25 | Street |
 * Street | 5 | | 26 | NickName | 昵称 | 5 | | 27 | StartHead | 开始头像 | 5 | | 28 |
 * ReputationValue | 信誉值 | 5 | | 29 | Mail | 邮箱 | 5 |
 * +----+-----------------+--------------------+---------+
 * log4j.appender.A3.layout.ConversionPattern=[%-1p][%d{yyMMdd
 * HH:mm:ss,SSS}][%l][%m]%n
 * 
 * @author sparrow
 *
 */
public enum PropertyTypes implements Serializable {
	Undefined(-1L), Experience(8L), UserSign(9L), Address(10L), PostCode(11L), Phone(
			12L), Sex(13L), DsFen(14L), TtFen(15L), RpValue(16L), CreditValue(
			17L), MlValue(18L), MaxGot(19L), BestBet(20L), Vip(21L), Country(
			22L), Province(23L), City(24L), Street(25L), NickName(26L), ReputationValue(
			28L), StartHead(27L), Mail(29L);

	public Long id;

	/**
	 * @param id
	 */
	private PropertyTypes(Long id) {
		this.id = id;
	}

	public static PropertyTypes getTypes(int value) {
		PropertyTypes t = Undefined;
		switch (value) {
		case 8:
			t = Experience;
			break;
		case 9:
			t = UserSign;
			break;
		case 10:
			t = Address;
			break;
		case 11:
			t = PostCode;
			break;
		case 12:
			t = Phone;
			break;
		case 13:
			t = Sex;
			break;
		case 14:
			t = DsFen;
			break;
		case 15:
			t = TtFen;
			break;
		case 16:
			t = RpValue;
			break;
		case 17:
			t = CreditValue;
			break;
		case 18:
			t = MlValue;
			break;
		case 19:
			t = MaxGot;
			break;
		case 20:
			t = BestBet;
			break;

		case 21:
			t = Vip;
			break;
		case 22:
			t = Country;
			break;
		case 23:
			t = Province;
			break;
		case 24:
			t = City;
			break;
		case 25:
			t = Street;
			break;
		case 26:
			t = NickName;
			break;
		case 27:
			t = ReputationValue;
			break;
		case 28:
			t = StartHead;
			break;
		case 29:
			t = Mail;
			break;
		

		default:
			break;
		}
		
		return t;
	}

}
