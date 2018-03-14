package com.sky.game.service.domain;

public class UserBillboard {

	public UserBillboard() {
		// TODO Auto-generated constructor stub
	}
	String NickName;
	String DsFen;
	String TtFen;
	String RpValue;
	String CreditValue;
	String MlValue;
	String MaxGot;
	String BestBet;
	String level;
	long rank;
	int gold;
	
	String iconImg;
	
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getNickName() {
		return NickName;
	}
	public void setNickName(String nickName) {
		NickName = nickName;
	}
	public String getDsFen() {
		return DsFen;
	}
	public void setDsFen(String dsFen) {
		DsFen = dsFen;
	}
	public String getTtFen() {
		return TtFen;
	}
	public void setTtFen(String ttFen) {
		TtFen = ttFen;
	}
	public String getRpValue() {
		return RpValue;
	}
	public void setRpValue(String rpValue) {
		RpValue = rpValue;
	}
	public String getCreditValue() {
		return CreditValue;
	}
	public void setCreditValue(String creditValue) {
		CreditValue = creditValue;
	}
	public String getMlValue() {
		return MlValue;
	}
	public void setMlValue(String mlValue) {
		MlValue = mlValue;
	}
	public String getMaxGot() {
		return MaxGot;
	}
	public void setMaxGot(String maxGot) {
		MaxGot = maxGot;
	}
	public String getBestBet() {
		return BestBet;
	}
	public void setBestBet(String bestBet) {
		BestBet = bestBet;
	}
	public long getRank() {
		return rank;
	}
	public void setRank(long rank) {
		this.rank = rank;
	}
	public int getGold() {
		return gold;
	}
	public void setGold(int gold) {
		this.gold = gold;
	}
	
	public String getIconImg() {
		return iconImg;
	}
	public void setIconImg(String iconImg) {
		this.iconImg = iconImg;
	}
	public UserBillboard(String nickName, String dsFen, String ttFen,String level,
			String rpValue, String creditValue, String mlValue, String maxGot,
			String bestBet, long rank, int gold,String iconImg) {
		super();
		NickName = nickName;
		DsFen = dsFen;
		TtFen = ttFen;
		RpValue = rpValue;
		CreditValue = creditValue;
		MlValue = mlValue;
		MaxGot = maxGot;
		BestBet = bestBet;
		this.rank = rank;
		this.gold = gold;
		this.iconImg=iconImg;
		this.level=level;
	}
	@Override
	public String toString() {
		return "UserBillboard [NickName=" + NickName + ", DsFen=" + DsFen
				+ ", TtFen=" + TtFen + ", RpValue=" + RpValue
				+ ", CreditValue=" + CreditValue + ", MlValue=" + MlValue
				+ ", MaxGot=" + MaxGot + ", BestBet=" + BestBet + ", rank="
				+ rank + ", gold=" + gold + ", iconImg=" + iconImg + ", level=" + level + "]";
	}
	
	
}
