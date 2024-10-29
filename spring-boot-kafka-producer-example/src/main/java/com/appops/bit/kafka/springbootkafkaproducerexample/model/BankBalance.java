package com.appops.bit.kafka.springbootkafkaproducerexample.model;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


public class BankBalance {

    private Long id;
    private BigDecimal amount = BigDecimal.ZERO;
    @JsonFormat(shape = JsonFormat.Shape.STRING,
            pattern = "dd-MM-yyyy hh:mm:ss")
    private Date lastUpdate;
    private BankTransaction latestTransaction;

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public BankTransaction getLatestTransaction() {
		return latestTransaction;
	}

	public void setLatestTransaction(BankTransaction latestTransaction) {
		this.latestTransaction = latestTransaction;
	}

	public BankBalance process(BankTransaction bankTransaction) {
        this.id = bankTransaction.getBalanceId();
        this.latestTransaction = bankTransaction;
        if(this.amount.add(bankTransaction.getAmount()).compareTo(BigDecimal.ZERO) >= 0) {
            this.latestTransaction.setState(BankTransaction.BankTransactionState.APPROVED);
            this.amount = this.amount.add(bankTransaction.getAmount());
        } else {
            this.latestTransaction.setState(BankTransaction.BankTransactionState.REJECTED);
        }
        this.lastUpdate = bankTransaction.getTime();
        return this;
    }
}
