package com.samuilolegovich.APIBanker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.samuilolegovich.APIBanker.model.db.Accounts;
import com.samuilolegovich.APIBanker.model.db.Clients;
import com.samuilolegovich.APIBanker.model.db.PaymentsJournal;
import com.samuilolegovich.APIBanker.model.exceptions.ClientNotFoundException;
import com.samuilolegovich.APIBanker.model.exceptions.DestNotFoundException;
import com.samuilolegovich.APIBanker.model.exceptions.SourceNotFoundException;
import com.samuilolegovich.APIBanker.model.inObjects.*;
import com.samuilolegovich.APIBanker.model.repo.*;
import com.samuilolegovich.APIBanker.model.repo.pgsql.AccountsRepositoryPG;
import com.samuilolegovich.APIBanker.model.repo.pgsql.ClientsRepositoryPG;
import com.samuilolegovich.APIBanker.model.requestObjects.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private AccountsRepositoryPG accountsRepositoryPG;
    @Autowired
    private ClientsRepositoryPG clientsRepositoryPG;
    @Autowired
    private ObjectMapper objectMapper;




    @GetMapping
    public ResponseEntity<String> page() {
        return new ResponseEntity<>("--- *** APIBanker *** ---", HttpStatus.OK);
    }



    @GetMapping("/client_id={id}")
    public ResponseEntity<AnswerClientID[]> findClient(@PathVariable(value = "id") long id) {
        // проверяем есть ли такой айди клиета если нет кидаем ошибку
        clientsRepository.findById(id).orElseThrow(ClientNotFoundException::new);
        // получаем все счета клиета создаем объекты выводв серелиазуем их и отправляем ответ
        List<Accounts> accountsList = customizedAccountsRepository.findAllByClientId(id);
        AnswerClientID[] answerClientIDS = new AnswerClientID[accountsList.size()];
        for (int i = 0; i < accountsList.size(); i++) {
            answerClientIDS[i] = new AnswerClientID(accountsList.get(i));
        }
        return new ResponseEntity<>(answerClientIDS, HttpStatus.OK);
    }


    @RequestMapping(value = "/client", method = RequestMethod.POST)
    @ResponseBody
//    @PostMapping("/client")
    public ResponseEntity<AnswerForNewClient> createClient(@RequestBody NewClient in) {
        Clients clientsDB = new Clients(in);
        clientsRepository.save(clientsDB);
        for (Account account : in.getAccounts()) {
            Accounts accountsDB = new Accounts(clientsDB, account);
            accountsRepository.save(accountsDB);
        }
        AnswerForNewClient answerForNewClient = new AnswerForNewClient(clientsDB);
        return new ResponseEntity<>(answerForNewClient, HttpStatus.CREATED);
    }




    @PostMapping("/create_payment")
    public ResponseEntity<AnswerForNewPay> createPayment(@RequestBody NewPay in) {
        // проверяем есть ли вообще такие платильщики
        clientsRepository.findById(in.getSource_acc_id()).orElseThrow(SourceNotFoundException::new);
        clientsRepository.findById(in.getDest_acc_id()).orElseThrow(DestNotFoundException::new);
        // получаем счета и сделать перевод
        AnswerForNewPay answerForNewPay = receiveInvoicesAndMakeTransfer(in);
        return new ResponseEntity<>(answerForNewPay, HttpStatus.CREATED);
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
    public ResponseEntity<AnswerForNewPays[]> createPayments(@RequestBody List<NewPay> in) {
        AnswerForNewPays[] answerForNewPaysArr = makeAllPayments(in);
        return new ResponseEntity<>(answerForNewPaysArr, HttpStatus.OK);
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
    public ResponseEntity<AnswerForPaymentJournal[]> getPaymentJournal(@RequestBody PaymentJournal in) {
        AnswerForPaymentJournal[] answerForPaymentJournalArr = getPaymentReport(in);
        return new ResponseEntity<>(answerForPaymentJournalArr, HttpStatus.OK);
    }

    private AnswerForPaymentJournal[] getPaymentReport(PaymentJournal paymentJournal) {
        List<PaymentsJournal> paymentsJournalList = customizedPaymentsJournalRepository
                .findAllBySourceAccIdAndDestAccId(paymentJournal.getSource_acc_id(),
                        paymentJournal.getDest_acc_id());
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
