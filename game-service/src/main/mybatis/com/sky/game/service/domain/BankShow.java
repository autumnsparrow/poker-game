package com.sky.game.service.domain;

public class BankShow {

	public BankShow() {
		// TODO Auto-generated constructor stub
	}
	long userId;
	String nickName;
	String startHead;
	int reputationValue;
	int deposit;
	int canDeposit;
	int loan;
	int canLoan;
	String loanDate;
	String refundDate;
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getStartHead() {
		return startHead;
	}
	public void setStartHead(String startHead) {
		this.startHead = startHead;
	}
	public int getReputationValue() {
		return reputationValue;
	}
	public void setReputationValue(int reputationValue) {
		this.reputationValue = reputationValue;
	}
	public int getDeposit() {
		return deposit;
	}
	public void setDeposit(int deposit) {
		this.deposit = deposit;
	}
	public int getCanDeposit() {
		return canDeposit;
	}
	public void setCanDeposit(int canDeposit) {
		this.canDeposit = canDeposit;
	}
	public int getLoan() {
		return loan;
	}
	public void setLoan(int loan) {
		this.loan = loan;
	}
	public int getCanLoan() {
		return canLoan;
	}
	public void setCanLoan(int canLoan) {
		this.canLoan = canLoan;
	}
	public String getLoanDate() {
		return loanDate;
	}
	public void setLoanDate(String loanDate) {
		this.loanDate = loanDate;
	}
	public String getRefundDate() {
		return refundDate;
	}
	public void setRefundDate(String refundDate) {
		this.refundDate = refundDate;
	}
	public BankShow(long userId, String nickName, String startHead,
			int reputationValue, int deposit, int canDeposit, int loan,
			int canLoan, String loanDate, String refundDate) {
		super();
		this.userId = userId;
		this.nickName = nickName;
		this.startHead = startHead;
		this.reputationValue = reputationValue;
		this.deposit = deposit;
		this.canDeposit = canDeposit;
		this.loan = loan;
		this.canLoan = canLoan;
		this.loanDate = loanDate;
		this.refundDate = refundDate;
	}
	@Override
	public String toString() {
		return "BankShow [userId=" + userId + ", nickName=" + nickName
				+ ", startHead=" + startHead + ", reputationValue="
				+ reputationValue + ", deposit=" + deposit + ", canDeposit="
				+ canDeposit + ", loan=" + loan + ", canLoan=" + canLoan
				+ ", loanDate=" + loanDate + ", refundDate=" + refundDate + "]";
	}
	
	
}
