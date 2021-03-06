package com.sky.game.service.domain;

import java.util.Date;

public class UserBank {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_bank.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_bank.user_account_id
     *
     * @mbggenerated
     */
    private Long userAccountId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_bank.loan_date
     *
     * @mbggenerated
     */
    private Date loanDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_bank.refund_date
     *
     * @mbggenerated
     */
    private Date refundDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_bank.deposit
     *
     * @mbggenerated
     */
    private Integer deposit;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_bank.loan
     *
     * @mbggenerated
     */
    private Integer loan;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_bank.bank_pw
     *
     * @mbggenerated
     */
    private String bankPw;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_bank.repayment
     *
     * @mbggenerated
     */
    private Integer repayment;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_bank.icon_id
     *
     * @mbggenerated
     */
    private Long iconId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_bank.id
     *
     * @return the value of user_bank.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_bank.id
     *
     * @param id the value for user_bank.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_bank.user_account_id
     *
     * @return the value of user_bank.user_account_id
     *
     * @mbggenerated
     */
    public Long getUserAccountId() {
        return userAccountId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_bank.user_account_id
     *
     * @param userAccountId the value for user_bank.user_account_id
     *
     * @mbggenerated
     */
    public void setUserAccountId(Long userAccountId) {
        this.userAccountId = userAccountId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_bank.loan_date
     *
     * @return the value of user_bank.loan_date
     *
     * @mbggenerated
     */
    public Date getLoanDate() {
        return loanDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_bank.loan_date
     *
     * @param loanDate the value for user_bank.loan_date
     *
     * @mbggenerated
     */
    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_bank.refund_date
     *
     * @return the value of user_bank.refund_date
     *
     * @mbggenerated
     */
    public Date getRefundDate() {
        return refundDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_bank.refund_date
     *
     * @param refundDate the value for user_bank.refund_date
     *
     * @mbggenerated
     */
    public void setRefundDate(Date refundDate) {
        this.refundDate = refundDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_bank.deposit
     *
     * @return the value of user_bank.deposit
     *
     * @mbggenerated
     */
    public Integer getDeposit() {
        return deposit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_bank.deposit
     *
     * @param deposit the value for user_bank.deposit
     *
     * @mbggenerated
     */
    public void setDeposit(Integer deposit) {
        this.deposit = deposit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_bank.loan
     *
     * @return the value of user_bank.loan
     *
     * @mbggenerated
     */
    public Integer getLoan() {
        return loan;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_bank.loan
     *
     * @param loan the value for user_bank.loan
     *
     * @mbggenerated
     */
    public void setLoan(Integer loan) {
        this.loan = loan;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_bank.bank_pw
     *
     * @return the value of user_bank.bank_pw
     *
     * @mbggenerated
     */
    public String getBankPw() {
        return bankPw;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_bank.bank_pw
     *
     * @param bankPw the value for user_bank.bank_pw
     *
     * @mbggenerated
     */
    public void setBankPw(String bankPw) {
        this.bankPw = bankPw == null ? null : bankPw.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_bank.repayment
     *
     * @return the value of user_bank.repayment
     *
     * @mbggenerated
     */
    public Integer getRepayment() {
        return repayment;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_bank.repayment
     *
     * @param repayment the value for user_bank.repayment
     *
     * @mbggenerated
     */
    public void setRepayment(Integer repayment) {
        this.repayment = repayment;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_bank.icon_id
     *
     * @return the value of user_bank.icon_id
     *
     * @mbggenerated
     */
    public Long getIconId() {
        return iconId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_bank.icon_id
     *
     * @param iconId the value for user_bank.icon_id
     *
     * @mbggenerated
     */
    public void setIconId(Long iconId) {
        this.iconId = iconId;
    }
}