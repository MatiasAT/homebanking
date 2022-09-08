package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDateTime;
import java.util.Arrays;


@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);}
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository, CardRepository cardRepository){
		return (args) ->{

			Client cliente1 = new Client("Melba","Morel", "melba@mindhub.com", passwordEncoder.encode("melba"));
			Client cliente2 = new Client("Melisa", "Castro","mcastro@gmail.com", passwordEncoder.encode("12345"));
			Client cliente3 = new Client("Matias", "Torres","mtorres@admin.com", passwordEncoder.encode("admin"));

			//Guarda las instancia cliente.
			clientRepository.save(cliente1);
			clientRepository.save(cliente2);
			clientRepository.save(cliente3);

			Account account1 = new Account("VIN-001000", LocalDateTime.now(), 5000, AccountType.AHORRO);
			Account account2 = new Account("VIN-002000", LocalDateTime.now().plusDays(1),7500, AccountType.CORRIENTE);
			Account account3 = new Account("VIN-003000", LocalDateTime.now().plusDays(4), 4300, AccountType.CORRIENTE);
			Account account4 = new Account("VIN-004000", LocalDateTime.now().plusDays(5), 5400, AccountType.AHORRO);


			cliente1.addAccount(account1);
			cliente1.addAccount(account3);
			cliente2.addAccount(account2);
			cliente3.addAccount(account4);

			accountRepository.save(account1);
			accountRepository.save(account3);
			accountRepository.save(account2);
			accountRepository.save(account4);

			Transaction transaction1 = new Transaction(TransactionType.CREDIT, "Transferencia recibida", LocalDateTime.now(), 2000, account1,account1.getBalance());
			transactionRepository.save(transaction1);

			account1.setBalance(account1.getBalance()+transaction1.getAmount());
			accountRepository.save(account1);


			Transaction transaction2 = new Transaction(TransactionType.DEBIT, "Transferencia recibida", LocalDateTime.now(), 500, account1,account1.getBalance());
			transactionRepository.save(transaction2);

			account1.setBalance(account1.getBalance()-transaction2.getAmount());
			accountRepository.save(account1);


			Loan prestamo1 = new Loan("Personal", 500000, Arrays.asList(12,24,36,48,60), 1.40, LoanType.PERSONAL);
			Loan prestamo2 = new Loan("Mortgage", 4000000, Arrays.asList(6,12,24),1.64, LoanType.MORTGAGE);
			Loan prestamo3 = new Loan("Car", 1000000, Arrays.asList(6,12,24,36),1.30, LoanType.CAR);
			Loan prestamo4 = new Loan("Personal_Admin", 200000, Arrays.asList(6,12),1.10, LoanType.PERSONAL_ADMIN);
			loanRepository.save(prestamo1);
			loanRepository.save(prestamo2);
			loanRepository.save(prestamo3);
			loanRepository.save(prestamo4);

			ClientLoan prestamocliente1 = new ClientLoan(400*prestamo1.getLoanRate(),4, cliente1, prestamo1);
			ClientLoan prestamocliente3 = new ClientLoan(500*prestamo2.getLoanRate(), 5, cliente3,prestamo2);

			clientLoanRepository.save(prestamocliente1);
			clientLoanRepository.save(prestamocliente3);

			Card debitCardGoldClient1 = new Card(cliente1.getFirstName()+ " " + cliente1.getLastName(), CardColor.GOLD, CardType.DEBIT,"112-4235-6371-4321","124", LocalDateTime.now().plusYears(5),LocalDateTime.now(),cliente1);
			Card creditCardSilverClient1 = new Card(cliente1.getFirstName()+ " " +cliente1.getLastName(), CardColor.SILVER,CardType.CREDIT, "112-4235-6371-43123", "152", LocalDateTime.now().plusYears(5), LocalDateTime.now(), cliente1);
			Card debitCardGoldClient3 = new Card(cliente3.getFirstName()+ " " + cliente3.getLastName(), CardColor.GOLD, CardType.DEBIT,"112-4235-1111-4321","124", LocalDateTime.now().plusYears(5),LocalDateTime.now(),cliente3);

			cardRepository.save(debitCardGoldClient1);
			cardRepository.save(creditCardSilverClient1);
			cardRepository.save(debitCardGoldClient3);


		};
	}

}

