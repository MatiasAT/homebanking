package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.models.LoanType;

import java.util.List;

public class LoanDto {

    private long id;

    private String name;
    private double maxAmount;

    private List<Integer> payments;
    private Double loanRate;
    private LoanType loanType;

    public LoanDto(){}

    public LoanDto(Loan loan){
        this.id = loan.getId();
        this.name = loan.getName();
        this.maxAmount = loan.getMaxAmount();
        this.payments = loan.getPayments();
        this.loanRate = loan.getLoanRate();
        this.loanType = loan.getLoanType();
    }

    public long getId() {
       return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public List<Integer> getPayments() {
        return payments;
    }

    public void setPayments(List<Integer> payments) {
        this.payments = payments;
    }

    public Double getLoanRate() {return loanRate;}

    public void setLoanRate(Double loanRate) {this.loanRate = loanRate;}

    public LoanType getLoanType() {
        return loanType;
    }

    public void setLoanType(LoanType loanType) {
        this.loanType = loanType;
    }
}
