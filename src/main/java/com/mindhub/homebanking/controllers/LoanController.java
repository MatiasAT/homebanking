package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.dtos.LoanAplicationDto;
import com.mindhub.homebanking.dtos.LoanDto;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.mindhub.homebanking.utils.UtilsTransaction.*;

@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    ClientLoanRepository clientLoanRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @GetMapping("/loans")
    public Set<LoanDto> getAllLoans(){
          return this.loanRepository.findAll().stream().map(LoanDto::new).collect(Collectors.toSet());
    }

    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<Object> newLoan(@RequestBody LoanAplicationDto loanAplicationDto, Authentication authentication){


        Double loanRate = loanRepository.findById(loanAplicationDto.getLoanId()).orElseThrow().getLoanRate();
        //long loanId, double amount, int payments,String toAccountNumber
        double amount = loanAplicationDto.getAmount();
        double loanAmount = amount*loanRate;
        Loan loan = loanRepository.getReferenceById(loanAplicationDto.getLoanId());
        int payment = loanAplicationDto.getPayments();
        boolean paymentsFalse = !(loan.getPayments().contains(payment));

        Client currentClient = clientRepository.findByEmail(authentication.getName());
        Account toAccount = accountRepository.findByClientAndNumber(currentClient, loanAplicationDto.getToAccountNumber());
        boolean dataLoanNull = loanAplicationDto.getLoanId()<1 || loanAplicationDto.getAmount()<1 || loanAplicationDto.getPayments()<1;
        boolean loanAccountNull = loanAplicationDto.getToAccountNumber().isEmpty() || toAccount == null;
        boolean nonExistingLoan = loan == null;



        if(dataLoanNull || loanAccountNull){
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if(nonExistingLoan){
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if(amount> loan.getMaxAmount()){
           return new ResponseEntity<>("you have exceeded the limit of allowed amount", HttpStatus.FORBIDDEN);
        }

        if(paymentsFalse){
            return new ResponseEntity<>("Missing data",HttpStatus.FORBIDDEN);
        }



        String descriptionLoans = loan.getName() + " || " + " paymens " + payment;
        ClientLoan newLoan = new ClientLoan(loanAmount,payment,currentClient,loan);
        newLoan.setAvailableAmount(loan.getMaxAmount()-amount);
        clientLoanRepository.save(newLoan);
        loanTransaction(descriptionLoans,amount,toAccount, transactionRepository, accountRepository );


        return new ResponseEntity<>("Loan approved",HttpStatus.ACCEPTED);
    }




}
