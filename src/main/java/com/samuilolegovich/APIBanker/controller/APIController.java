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
import com.samuilolegovich.APIBanker.model.repo.AccountsRepository;
import com.samuilolegovich.APIBanker.model.repo.ClientsRepository;
import com.samuilolegovich.APIBanker.model.repo.CustomizedAccountsRepository;
import com.samuilolegovich.APIBanker.model.repo.PaymentsJournalRepository;
import com.samuilolegovich.APIBanker.model.requestObjects.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
        System.out.println(out);
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
        System.out.println(out);
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
        AnswerForPaymentJournal answerForPaymentJournal;
        PaymentJournal paymentJournal;
        String out = null;
        // "payer_id": 123,
        // "recipient_id": 124,
        // "source_acc_id": 456,
        // "dest_acc_id": 457

        try {
            paymentJournal = objectMapper.readValue(in, PaymentJournal.class);
            answerForPaymentJournal = getPaymentReport(paymentJournal);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return out;
    }

    private AnswerForPaymentJournal getPaymentReport(PaymentJournal paymentJournal) {
        ArrayList<AnswerForPaymentJournal> answerForPaymentJournalList = new ArrayList<>();
        answerForPaymentJournalList.addAll(getForPayerId(paymentJournal.getPayer_id()));
        answerForPaymentJournalList.addAll(getForRecipientId(paymentJournal.getRecipient_id()));
        answerForPaymentJournalList.addAll(getForSourceAccId(paymentJournal.getSource_acc_id()));
        answerForPaymentJournalList.addAll(getForDestAccId(paymentJournal.getDest_acc_id()));
        // "payment_id": 159,
        //"timestamp": "2020-08-25 13:18:54",
        // "src_acc_num":
        // "123456789",
        // "dest_acc_num":
        // "987654321",
        // "amount": 1000.00,
        //
        //"payer": {
        //"first_name": "Имя",
        //"last_name": "Имя" },
        //"recipient": {
        // "first_name": "Имя",
        //"last_name": "Имя"


        // "payer_id": 123,
        // "recipient_id": 124,
        // "source_acc_id": 456,
        // "dest_acc_id": 457

        return null;
    }

    // находим всю иформацию по расходам пользователя
    private ArrayList<AnswerForPaymentJournal> getForPayerId(long in) {
        // "payer_id": 123, - платильщик

        // "payment_id": 159,
        //"timestamp": "2020-08-25 13:18:54",
        // "src_acc_num":
        // "123456789",
        // "dest_acc_num":
        // "987654321",
        // "amount": 1000.00,
        //
        //"payer": {
        //"first_name": "Имя",
        //"last_name": "Имя" },
        //"recipient": {
        // "first_name": "Имя",
        //"last_name": "Имя"


        return null;
    }

    // находим всю информацию по поступлениям пользователя
    private ArrayList<AnswerForPaymentJournal> getForRecipientId(long in) {
        // "recipient_id": 124,

        // "payment_id": 159,
        //"timestamp": "2020-08-25 13:18:54",
        // "src_acc_num":
        // "123456789",
        // "dest_acc_num":
        // "987654321",
        // "amount": 1000.00,
        //
        //"payer": {
        //"first_name": "Имя",
        //"last_name": "Имя" },
        //"recipient": {
        // "first_name": "Имя",
        //"last_name": "Имя"

        return null;
    }

    private ArrayList<AnswerForPaymentJournal> getForSourceAccId(long in) {

        return null;
    }

    private ArrayList<AnswerForPaymentJournal> getForDestAccId(long in) {

        return null;
    }
}
