package com.samuilolegovich.APIBanker.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.samuilolegovich.APIBanker.model.db.Accounts;
import com.samuilolegovich.APIBanker.model.db.Clients;
import com.samuilolegovich.APIBanker.model.db.PaymentsJournal;
import com.samuilolegovich.APIBanker.model.exceptions.NotFoundException;
import com.samuilolegovich.APIBanker.model.inObjects.Account;
import com.samuilolegovich.APIBanker.model.inObjects.NewClient;
import com.samuilolegovich.APIBanker.model.inObjects.NewPay;
import com.samuilolegovich.APIBanker.model.repo.AccountsRepository;
import com.samuilolegovich.APIBanker.model.repo.ClientsRepository;
import com.samuilolegovich.APIBanker.model.repo.CustomizedAccountsRepository;
import com.samuilolegovich.APIBanker.model.repo.PaymentsJournalRepository;
import com.samuilolegovich.APIBanker.model.requestObjects.AnswerClientID;
import com.samuilolegovich.APIBanker.model.requestObjects.AnswerForNewClient;
import com.samuilolegovich.APIBanker.model.requestObjects.AnswerForNewPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
        String out = "";
//        if (!clientsRepository.existsById(id)) { return ""; }
        // проверяем есть ли такой айди клиета если нет кидаем ошибку
        clientsRepository.findById(id).orElseThrow(NotFoundException::new);
        // получаем все счета клиета создаем объекты выводв серелиазуем их и отправляем ответ
        List<Accounts> accountsList = customizedAccountsRepository.findAllByClientId(id);
        AnswerClientID[] answerClientIDS = new AnswerClientID[accountsList.size()];
        for (int i = 0; i < accountsList.size(); i++) {
            answerClientIDS[i] = new AnswerClientID(accountsList.get(i));
        }
        try {
            out = objectMapper.writeValueAsString(answerClientIDS);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return out;
    }


    @PostMapping("/client")
    public String createClient(@RequestBody String in) {
        AnswerForNewClient answerForNewClient = null;
        String out = "";
        try {
            newClient = objectMapper.readValue(in, NewClient.class);
            Clients clientsDB = new Clients(newClient);
            clientsRepository.save(clientsDB);
            answerForNewClient = new AnswerForNewClient(clientsDB);
            for (Account account : newClient.getAccounts()) {
                Accounts accountsDB = new Accounts(clientsDB, account);
                accountsRepository.save(accountsDB);
            }
            try {
                out = objectMapper.writeValueAsString(answerForNewClient);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return out;
    }


    @PostMapping("/create_payment")
    public String createPayment(@RequestBody String in) {
        AnswerForNewPay answerForNewPay = null;
        String out = "";

        try {
            newPay = objectMapper.readValue(in, NewPay.class);
            // проверяем есть ли вообще такие платильщики
            clientsRepository.findById(newPay.getSource_acc_id()).orElseThrow(NotFoundException::new);
            clientsRepository.findById(newPay.getDest_acc_id()).orElseThrow(NotFoundException::new);
            // получаем их счета
            List<Accounts> sourceAccList = customizedAccountsRepository.findAllByClientId(newPay.getSource_acc_id());
            List<Accounts> destAccList = customizedAccountsRepository.findAllByClientId(newPay.getDest_acc_id());
            for (Accounts accounts : sourceAccList) {
                if (accounts.getBalance() >= newPay.getAmount()) {
                    answerForNewPay = makeTransferOfFunds(newPay, accounts, destAccList.get(0));
                    break;
                }
            }
            out = objectMapper.writeValueAsString(answerForNewPay);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return out;
    }

    private AnswerForNewPay makeTransferOfFunds(NewPay newPay, Accounts source, Accounts dest) {
        source.setBalance(source.getBalance() - newPay.getAmount());
        dest.setBalance(dest.getBalance() + newPay.getAmount());
        PaymentsJournal paymentsJournal = new PaymentsJournal(source, dest, newPay);
        AnswerForNewPay answerForNewPay = new AnswerForNewPay(paymentsJournal);
        paymentsJournalRepository.save(paymentsJournal);
        accountsRepository.save(source);
        accountsRepository.save(dest);
        return answerForNewPay;
    }



    @PostMapping("/create_payments")
    public String createPayments(@RequestBody String in) {
        NewPay[] newPays;
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
