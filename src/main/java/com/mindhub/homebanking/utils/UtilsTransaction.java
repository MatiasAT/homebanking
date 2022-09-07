package com.mindhub.homebanking.utils;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;

import java.time.LocalDateTime;

public class UtilsTransaction {

    private UtilsTransaction(){}

    static public void newTransaction(String description, double fromAmount, Account fromClientAccount, Account toAccount, TransactionRepository transactionRepository, AccountRepository accountRepository){
        double debitAmount = fromAmount*(-1);
        String transactionNameDebit = " from " + TransactionType.DEBIT.name();
        String transactionNameCredit = " to " + TransactionType.CREDIT.name();

        Transaction debitTransaction = new Transaction(TransactionType.DEBIT, description +" || "+ toAccount.getNumber() +" || "+ transactionNameDebit, LocalDateTime.now(),debitAmount,fromClientAccount, fromClientAccount.getBalance());
        double creditAmount = Math.abs(debitTransaction.getAmount());
        Transaction creditTransaction = new Transaction(TransactionType.CREDIT, description +" || "+ fromClientAccount.getNumber() +" || "+ transactionNameCredit, debitTransaction.getDate(),creditAmount,toAccount, toAccount.getBalance());

        transactionRepository.save(debitTransaction);
        transactionRepository.save(creditTransaction);

        double currentAccountBalance = debitOperation(fromClientAccount.getBalance(),debitTransaction.getAmount());
        double toAccountBalance = creditOperation(toAccount.getBalance(),debitTransaction.getAmount());

        fromClientAccount.setBalance(currentAccountBalance);
        toAccount.setBalance(toAccountBalance);

        accountRepository.save(fromClientAccount);
        accountRepository.save(toAccount);
    }

    static public void loanTransaction(String description, double amount, Account toAccount, TransactionRepository transactionRepository, AccountRepository accountRepository){

        String transactionNameCredit = TransactionType.CREDIT.name();

        Transaction creditTransaction = new Transaction(TransactionType.CREDIT, description +" || "+ transactionNameCredit, LocalDateTime.now(), amount,toAccount, toAccount.getBalance());

        transactionRepository.save(creditTransaction);

        double toAccountBalance = creditOperation(toAccount.getBalance(),amount);

        toAccount.setBalance(toAccountBalance);

        accountRepository.save(toAccount);
    }

    static private double debitOperation(double fromBalance, double fromAmount){
        return (fromBalance - Math.abs(fromAmount));
    }

    static public double creditOperation(double toBalance, double toAmount){
        return (toBalance + Math.abs(toAmount));
    }
}