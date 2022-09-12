package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.dtos.ClientDto;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface CardRepository extends JpaRepository<Card,Long>{

    List<Card> findByClientAndCardType(Client client, CardType cardType);
    Card findByClientAndNumber(Client client, String number);
    Card findByCvv(String cvv);
    Card findByNumber(String number);
    Card findByNumberAndCvv(String number, String cvv);

}
