package com.mindhub.homebanking.utils;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.repositories.AccountRepository;


public class UtilsAccount {

    private UtilsAccount(){}

    public static String newAccountNumber(AccountRepository accountRepository) {
        Account numberAccount;
        String randomNumberAccount;

        do {

            //concatena y convierte el numero aleatorio en un String numero de cuenta
            randomNumberAccount = "VIN-" + String.format("%06d", getRandomNumber(1,999999));
            //Lo busca en la base, si no esta devuelve un null
            numberAccount = accountRepository.findByNumber(randomNumberAccount);

        } while (numberAccount != null);
        return randomNumberAccount;
    }

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

}

