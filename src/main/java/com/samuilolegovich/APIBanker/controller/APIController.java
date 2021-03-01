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
import com.samuilolegovich.APIBanker.model.inObjects.PaymentJournal;
import com.samuilolegovich.APIBanker.model.repo.*;
import com.samuilolegovich.APIBanker.model.requestObjects.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@RestController
@RequestMapping("api")
public class APIController {
    @Autowired
    private CustomizedPaymentsJournalRepository customizedPaymentsJournalRepository;
    @Autowired
    private CustomizedAccountsRepository customizedAccountsRepository;
    @Autowired
    private PaymentsJournalRepository paymentsJournalRepository;
    @Autowired
    private AccountsRepository accountsRepository;
    @Autowired
    private ClientsRepository clientsRepository;
    @Autowired
    private ObjectMapper objectMapper;



    @GetMapping
    public String page() {
        return "--- *** APIBanker *** ---";
    }



    @GetMapping("/client_id={id}")
    public String findClient(@PathVariable(value = "id") long id) {
        String out = null;
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
        NewClient newClient;
        String out = null;
        try {
            newClient = objectMapper.readValue(in, NewClient.class);
            Clients clientsDB = new Clients(newClient);
            clientsRepository.save(clientsDB);
            answerForNewClient = new AnswerForNewClient(clientsDB);
            for (Account account : newClient.getAccounts()) {
                Accounts accountsDB = new Accounts(clientsDB, account);
                accountsRepository.save(accountsDB);
            }
            out = objectMapper.writeValueAsString(answerForNewClient);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return out;
    }



    @PostMapping("/create_payment")
    public String createPayment(@RequestBody String in) {
        AnswerForNewPay answerForNewPay = null;
        String out = null;
        NewPay newPay;

        try {
            newPay = objectMapper.readValue(in, NewPay.class);
            // проверяем есть ли вообще такие платильщики
            clientsRepository.findById(newPay.getSource_acc_id()).orElseThrow(NotFoundException::new);
            clientsRepository.findById(newPay.getDest_acc_id()).orElseThrow(NotFoundException::new);
            // получаем счета и сделать перевод
            answerForNewPay = receiveInvoicesAndMakeTransfer(newPay);
            out = objectMapper.writeValueAsString(answerForNewPay);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return out;
    }

    private AnswerForNewPay receiveInvoicesAndMakeTransfer(NewPay newPay) {
        // получаем счета
        List<Accounts> sourceAccList = customizedAccountsRepository.findAllByClientId(newPay.getSource_acc_id());
        List<Accounts> destAccList = customizedAccountsRepository.findAllByClientId(newPay.getDest_acc_id());
        for (Accounts accounts : sourceAccList) {
            if (accounts.getBalance() >= newPay.getAmount()) {
                return makeTransferOfFunds(newPay, accounts, destAccList.get(0));
            }
        }
        return null;
    }

    private AnswerForNewPay makeTransferOfFunds(NewPay newPay, Accounts source, Accounts dest) {
        source.setBalance(source.getBalance() - newPay.getAmount());
        dest.setBalance(dest.getBalance() + newPay.getAmount());
        PaymentsJournal paymentsJournal = new PaymentsJournal(source, dest, newPay);
        paymentsJournalRepository.save(paymentsJournal);
        AnswerForNewPay answerForNewPay = new AnswerForNewPay(paymentsJournal);
        accountsRepository.save(source);
        accountsRepository.save(dest);
        return answerForNewPay;
    }



    @PostMapping("/create_payments")
    public String createPayments(@RequestBody String in) {
        AnswerForNewPays[] answerForNewPaysArr;
        List<NewPay> newPayList = null;
        String out = null;

        try {
            newPayList = objectMapper.readValue(in, List.class);
            answerForNewPaysArr = makeAllPayments(newPayList);
            out = objectMapper.writeValueAsString(answerForNewPaysArr);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return out;
    }

    private AnswerForNewPays[] makeAllPayments(List<NewPay> newPayList) {
        AnswerForNewPays[] answerForNewPaysArr = new AnswerForNewPays[newPayList.size()];
        for (NewPay newPay : newPayList) {
            // проверяем есть ли такой платильщик и получатель
            if (!clientsRepository.existsById(newPay.getSource_acc_id())
                    || !clientsRepository.existsById(newPay.getDest_acc_id())) {
                answerForNewPaysArr[newPayList.indexOf(newPay)] = notMakeTransferOfFunds(newPay);
            } else {
                answerForNewPaysArr[newPayList.indexOf(newPay)] =
                        new AnswerForNewPays(Objects.requireNonNull(receiveInvoicesAndMakeTransfer(newPay)));
            }
        }
        return answerForNewPaysArr;
    }

    private AnswerForNewPays notMakeTransferOfFunds(NewPay newPay) {
        PaymentsJournal paymentsJournal = new PaymentsJournal(newPay);
        AnswerForNewPays answerForNewPays = new AnswerForNewPays(paymentsJournal.getPayment_id(),
                Status.error.toString());
        paymentsJournalRepository.save(paymentsJournal);
        return answerForNewPays;
    }



    @PostMapping("/payment_journal")
    public String getPaymentJournal(@RequestBody String in) {
        AnswerForPaymentJournal[] answerForPaymentJournalArr;
        PaymentJournal paymentJournal;
        String out = null;
        try {
            paymentJournal = objectMapper.readValue(in, PaymentJournal.class);
            answerForPaymentJournalArr = getPaymentReport(paymentJournal);
            out = objectMapper.writeValueAsString(answerForPaymentJournalArr);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return out;
    }

    private AnswerForPaymentJournal[] getPaymentReport(PaymentJournal paymentJournal) {
        List<PaymentsJournal> paymentsJournalList = customizedPaymentsJournalRepository
                .findAllBySourceAccIdAndDestAccId(paymentJournal.getSource_acc_id(),
                        paymentJournal.getDest_acc_id());
//                .orElseThrow(NotFoundException::new);
        AnswerForPaymentJournal[] answerForPaymentJournalsArr = new AnswerForPaymentJournal[paymentsJournalList.size()];
        Optional<Clients> optionalClientsRecipient = clientsRepository.findById(paymentJournal.getRecipient_id());
        Optional<Clients> optionalClientsPayer = clientsRepository.findById(paymentJournal.getPayer_id());
        Clients clientsRecipient = optionalClientsRecipient.get();
        Clients clientsPayer = optionalClientsPayer.get();

        for (PaymentsJournal paymentsJournal : paymentsJournalList) {
            answerForPaymentJournalsArr[paymentsJournalList.indexOf(paymentsJournal)]
                    = new AnswerForPaymentJournal(paymentsJournal, clientsPayer, clientsRecipient);
        }
        return answerForPaymentJournalsArr;
    }
}
