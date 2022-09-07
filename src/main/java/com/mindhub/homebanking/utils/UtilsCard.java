package com.mindhub.homebanking.utils;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.repositories.CardRepository;

public class UtilsCard {

    private UtilsCard(){}

    public static String cardNumber(CardRepository cardRepository){

       Card newCardNumber;

        do{
            newCardNumber = cardRepository.findByNumber(randomCardNumber());
        }while(newCardNumber != null);

        return randomCardNumber();
    }

    public static String randomCardNumber(){
        StringBuffer number = new StringBuffer();
        int groupNumber = 4;
        for (int group=1; group<=groupNumber;group++){
            number.append(String.format("%04d",getRandomNumber(1,999)));
            //cardnumber.append(getRandomNumber(1,9999));
            if(group< groupNumber){
                number.append("-");
            }

        }
        return String.valueOf(number);
    }

    public static String cvvNumber(CardRepository cardRepository){

        Card newCvv;
        String randomNumber;

        do{
            randomNumber = String.format("%03d", getRandomNumber(1,999));
            newCvv = cardRepository.findByCvv(randomNumber);
        }while(newCvv!=null);

        return randomNumber;
    }
    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
