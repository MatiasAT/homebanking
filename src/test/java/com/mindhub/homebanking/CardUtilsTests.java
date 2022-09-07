package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.utils.UtilsCard;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.hamcrest.Matchers.*;


@SpringBootTest
public class CardUtilsTests {

    @Autowired
    CardRepository cardRepository;


    @Test
    public void cardNumberIsCreated(){
        String cardNumber = UtilsCard.cardNumber(cardRepository);
        MatcherAssert.assertThat(cardNumber, is(not(emptyOrNullString())));

    }
    @Test
    public void cardCvvIsCreated(){
        String cvv = UtilsCard.cvvNumber(cardRepository);
        MatcherAssert.assertThat(cvv, is(not(emptyOrNullString())));
    }
}
