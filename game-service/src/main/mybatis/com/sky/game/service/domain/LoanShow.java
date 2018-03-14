package com.sky.game.service.domain;

import java.util.Date;

public class LoanShow {

	public LoanShow() {
		// TODO Auto-generated constructor stub
	}
	int userLoan;
	int maxLoan;
	int userRepayment;
	Date loanDate;
	Date refundDate;
	public int getUserLoan() {
		return userLoan;
	}
	public void setUserLoan(int userLoan) {
		this.userLoan = userLoan;
	}
	public int getMaxLoan() {
		return maxLoan;
	}
	public void setMaxLoan(int maxLoan) {
		this.maxLoan = maxLoan;
	}
	public int getUserRepayment() {
		return userRepayment;
	}
	public void setUserRepayment(int userRepayment) {
		this.userRepayment = userRepayment;
	}
	public Date getLoanDate() {
		return loanDate;
	}
	public void setLoanDate(Date loanDate) {
		this.loanDate = loanDate;
	}
	public Date getRefundDate() {
		return refundDate;
	}
	public void setRefundDate(Date refundDate) {
		this.refundDate = refundDate;
	}
	public LoanShow(int userLoan, int maxLoan, int userRepayment,
			Date loanDate, Date refundDate) {
		super();
		this.userLoan = userLoan;
		this.maxLoan = maxLoan;
		this.userRepayment = userRepayment;
		this.loanDate = loanDate;
		this.refundDate = refundDate;
	}
	@Override
	public String toString() {
		return "LoanShow [userLoan=" + userLoan + ", maxLoan=" + maxLoan
				+ ", userRepayment=" + userRepayment + ", loanDate=" + loanDate
				+ ", refundDate=" + refundDate + "]";
	}
	
}
