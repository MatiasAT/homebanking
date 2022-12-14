package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.loader.custom.FetchReturn;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private String firstName, lastName, email, password;

    @OneToMany(mappedBy="client", fetch=FetchType.EAGER)
    private Set<Account> accountSet = new HashSet<>();

    @OneToMany(mappedBy ="client", fetch = FetchType.EAGER)
    private Set<ClientLoan> loanSet = new HashSet<>();

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<Card> cardSet = new HashSet<>();

    public Client(){}

    public Client(String firstName, String lastName, String email, String password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Account> getAccountSet() {
        return accountSet;
    }

    public void addAccount(Account account){
        account.setClient(this);
        this.accountSet.add(account);
    }

    public Set<ClientLoan> getLoanSet() {
        return loanSet;
    }

    public void setLoanSet(Set<ClientLoan> loanSet) {
        this.loanSet = loanSet;
    }

    public Set<Card> getCardSet() {
        return cardSet;
    }

    public void setCardSet(Set<Card> cardSet) {
        this.cardSet = cardSet;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
