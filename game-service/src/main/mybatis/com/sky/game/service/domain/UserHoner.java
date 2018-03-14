/**
 * 
 */
package com.sky.game.service.domain;

/**
 * @author Administrator
 *
 */
public class UserHoner {

	/**
	 * 
	 */
	public UserHoner() {
		// TODO Auto-generated constructor stub
	}
	String DsFen;
	String TtFen;
	String RpValue;
	String CreditValue;
	String MlValue;
	String MaxGot;
	String BestBet;
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
	public UserHoner(String dsFen, String ttFen, String rpValue,
			String creditValue, String mlValue, String maxGot, String bestBet) {
		super();
		DsFen = dsFen;
		TtFen = ttFen;
		RpValue = rpValue;
		CreditValue = creditValue;
		MlValue = mlValue;
		MaxGot = maxGot;
		BestBet = bestBet;
	}
	@Override
	public String toString() {
		return "UserHoner [DsFen=" + DsFen + ", TtFen=" + TtFen + ", RpValue="
				+ RpValue + ", CreditValue=" + CreditValue + ", MlValue="
				+ MlValue + ", MaxGot=" + MaxGot + ", BestBet=" + BestBet + "]";
	}

}
