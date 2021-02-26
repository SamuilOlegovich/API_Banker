package com.samuilolegovich.APIBanker.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.samuilolegovich.APIBanker.model.db.Accounts;
import com.samuilolegovich.APIBanker.model.db.Clients;
import com.samuilolegovich.APIBanker.model.exceptions.NotFoundException;
import com.samuilolegovich.APIBanker.model.inObjects.Account;
import com.samuilolegovich.APIBanker.model.inObjects.NewClient;
import com.samuilolegovich.APIBanker.model.inObjects.NewPay;
import com.samuilolegovich.APIBanker.model.repo.AccountsRepository;
import com.samuilolegovich.APIBanker.model.repo.ClientsRepository;
import com.samuilolegovich.APIBanker.model.repo.CustomizedAccountsRepository;
import com.samuilolegovich.APIBanker.model.repo.PaymentsJournalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("api")
public class APIController {
    @Autowired
    private PaymentsJournalRepository paymentsJournalRepository;
    @Autowired
    CustomizedAccountsRepository customizedAccountsRepository;
    @Autowired
    private AccountsRepository accountsRepository;
    @Autowired
    private ClientsRepository clientsRepository;
    @Autowired
    private ObjectMapper objectMapper;
    private NewClient newClient = null;
    private NewPay[] newPays = null;
    private NewPay newPay = null;



    @GetMapping
    public String page() {
        return "{API}";
    }

    @GetMapping("/client_id={id}")
    public String findClient(@PathVariable(value = "id") long id) {
//        if (!clientsRepository.existsById(id)) { return ""; }
        Clients clients = clientsRepository.findById(id).orElseThrow(NotFoundException::new);
//        Optional<Accounts> optionalAccounts = customizedAccountsRepository.findAllByClientId(id);
        List<Accounts> accountsList = customizedAccountsRepository.findAllByClientId(id);
//        List<Accounts> accountsList = new ArrayList<>();
//        optionalAccounts.ifPresent(accountsList::add);
//        System.out.println(accountsList.size());

//        for (Accounts accounts : accountsList) {
//            System.out.println(accounts.getAccount_type());
//        }
//
        for (Accounts accounts : accountsList) {
            System.out.println(accounts.getAccount_type());
        }
        String out = "";

        try {
            out = objectMapper.writeValueAsString(accountsList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

//        AnswerClientID[] answerClientIDS;
        // тут мы реализуем поиск акканутов (счетов) клиента по заданому айди


//        return arrayList.stream().findFirst().orElseThrow(NotFoundException::new);
        return out;
    }


    @PostMapping("/client")
    public String createClient(@RequestBody String in) {
        // создать клиента и уго счета
        // вернуть Ответ:
        // Код http: 201 - добавление успешно
        // {"client_id": 123}
//        NewClient newClient = null;
        try {
            newClient = objectMapper.readValue(in, NewClient.class);
            Clients clientsDB = new Clients(newClient);
            clientsRepository.save(clientsDB);
            for (Account account : newClient.getAccounts()) {
                Accounts accountsDB = new Accounts(clientsDB, account);
                accountsRepository.save(accountsDB);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        return "RETURN";
    }


    @PostMapping("/create_payment")
    public String createPayment(@RequestBody String in) {
        try {
            newPay = objectMapper.readValue(in, NewPay.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return "RETURN";
    }



    @PostMapping("/create_payments")
    public String createPayments(@RequestBody String in) {
        try {
            newPays = objectMapper.readValue(in, NewPay[].class);;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return "RETURN";
    }


    @PostMapping("/payment_journal")
    public String getPaymentJournal(@RequestBody String in) {
        try {
            newPays = objectMapper.readValue(in, NewPay[].class);;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return "RETURN";
    }



}
