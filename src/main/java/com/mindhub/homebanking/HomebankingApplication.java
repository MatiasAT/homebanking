package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.models.Transaction;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository){
		return (args) ->{


			Client cliente1 = new Client("Melba","Morel", "melba@mindhub.com");
			Client cliente2 = new Client("Melisa", "Castro","mcastro@gmail.com");

			//Guarda las instancia cliente.
			clientRepository.save(cliente1);
			clientRepository.save(cliente2);

			Account account1 = new Account("VIN001", LocalDateTime.now(), 5000);
			Account account2 = new Account("VIN002", LocalDateTime.now().plusDays(1),7500);
			Account account3 = new Account("VIN003", LocalDateTime.now().plusDays(4), 4300);


			cliente1.addAccount(account1);
			cliente1.addAccount(account3);
			cliente2.addAccount(account2);

			accountRepository.save(account1);
			accountRepository.save(account3);
			accountRepository.save(account2);

			Transaction transaction1 = new Transaction(TransactionType.CREDIT, "Transferencia recibida", LocalDateTime.now(), 2000, account1);

			transactionRepository.save(transaction1);
		};
	}

}

