package com.sky.game.manager.remote.activerecord;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooJpaActiveRecord(versionField = "", sequenceName = "", table = "user_bank")
//@RooDbManaged(automaticallyDelete = true)
@RooToString(excludeFields = { "userAccountId" })

public class UserBank {
	
	@ManyToOne
    @JoinColumn(name = "user_account_id", referencedColumnName = "id", nullable = false)
    private UserAccount userAccountId;

	@Column(name = "loan_date")
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Calendar loanDate;

	@Column(name = "refund_date")
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Calendar refundDate;

	@Column(name = "deposit")
    private Integer deposit;

	@Column(name = "loan")
    private Integer loan;

	@Column(name = "bank_pw", length = 60)
    private String bankPw;

	@Column(name = "repayment")
    private Integer repayment;

	public UserAccount getUserAccountId() {
        return userAccountId;
    }

	public void setUserAccountId(UserAccount userAccountId) {
        this.userAccountId = userAccountId;
    }

	public Calendar getLoanDate() {
        return loanDate;
    }

	public void setLoanDate(Calendar loanDate) {
        this.loanDate = loanDate;
    }

	public Calendar getRefundDate() {
        return refundDate;
    }

	public void setRefundDate(Calendar refundDate) {
        this.refundDate = refundDate;
    }

	public Integer getDeposit() {
        return deposit;
    }

	public void setDeposit(Integer deposit) {
        this.deposit = deposit;
    }

	public Integer getLoan() {
        return loan;
    }

	public void setLoan(Integer loan) {
        this.loan = loan;
    }

	public String getBankPw() {
        return bankPw;
    }

	public void setBankPw(String bankPw) {
        this.bankPw = bankPw;
    }

	public Integer getRepayment() {
        return repayment;
    }

	public void setRepayment(Integer repayment) {
        this.repayment = repayment;
    }
}
