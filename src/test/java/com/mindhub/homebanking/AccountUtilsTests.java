package com.mindhub.homebanking;

import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.utils.UtilsAccount;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.hamcrest.Matchers.*;


@SpringBootTest
public class AccountUtilsTests {

    @Autowired
    AccountRepository accountRepository;

    @Test
    public void AccountNumberCreated(){
        String accountNumber = UtilsAccount.newAccountNumber(accountRepository);
        MatcherAssert.assertThat(accountNumber, is(not(emptyOrNullString())));
    }
}
