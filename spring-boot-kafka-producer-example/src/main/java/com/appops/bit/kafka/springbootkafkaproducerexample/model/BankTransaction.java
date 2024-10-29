package com.appops.bit.kafka.springbootkafkaproducerexample.model;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


public class BankTransaction {

    private Long id;
    private Long balanceId;
    private BigDecimal amount;
    @JsonFormat(shape = JsonFormat.Shape.STRING,
                pattern = "dd-MM-yyyy hh:mm:ss")
    public Date time;
    public BankTransactionState state = BankTransactionState.CREATED;

    public static enum BankTransactionState {
        CREATED, APPROVED, REJECTED
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBalanceId() {
		return balanceId;
	}

	public void setBalanceId(Long balanceId) {
		this.balanceId = balanceId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public BankTransactionState getState() {
		return state;
	}

	public void setState(BankTransactionState state) {
		this.state = state;
	}	
    
	public BankTransaction(BankTransaction bankTransaction) {
		this.id=bankTransaction.id;
		this.balanceId=bankTransaction.balanceId;
		this.amount=bankTransaction.amount;
		this.time=bankTransaction.time;
		this.state=bankTransaction.state;
	}

	public BankTransaction() {
	}

	public static BankTransaction builder() {
		BankTransaction bankTransaction = new BankTransaction();
		return bankTransaction;

        
	}
	
	public BankTransaction balanceId(Long balanceId) {
        this.balanceId = balanceId;
        return this;
    }

	public BankTransaction time(Date time) {
        this.time = time;
        return this;
    }

	public BankTransaction amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }
    
	public BankTransaction build() {
        return new BankTransaction(this);
    }
    
}
