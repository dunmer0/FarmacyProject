package main.com.ubb.postuniv.farmacy.Service;

import main.com.ubb.postuniv.farmacy.Domain.*;
import main.com.ubb.postuniv.farmacy.Repository.IRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class TransactionController {
    IRepository<Transaction> transactionRepository;
    IRepository<Medicine> medicineRepository;
    IRepository<CardClient> cardClientRepository;

    public TransactionController(IRepository<Transaction> transactionRepository,
                                 IRepository<Medicine> medicineRepository,
                                 IRepository<CardClient> cardClientRepository) {
        this.transactionRepository = transactionRepository;
        this.medicineRepository = medicineRepository;
        this.cardClientRepository = cardClientRepository;
    }

    public void add(int id, int idMedicine, int idCardClient, int numberOfMedicine, LocalDateTime transactionDate) throws DuplicateException, NullException {
        Transaction transaction = new Transaction(id, idMedicine, idCardClient, numberOfMedicine, transactionDate);
        Medicine drug = medicineRepository.readOne(idMedicine);
        if (drug == null) {
            throw new NullException("The drug does not exist");
        }
        CardClient cardClient = cardClientRepository.readOne(idCardClient);
        if (cardClient != null) {
            if (drug.NeedsPrescription()) {
                transaction.setTotalPrice(drug.getPrice() * transaction.getNumberOfMedicine() * 0.85f);
            } else {
                transaction.setTotalPrice(drug.getPrice() * transaction.getNumberOfMedicine() * .90f);
            }
        } else {
            transaction.setTotalPrice(drug.getPrice() * transaction.getNumberOfMedicine());
        }
        this.transactionRepository.create(transaction);
    }

    public List<Transaction> getAll() {
        return new ArrayList<>(this.transactionRepository.readAll());
    }

    public Transaction getOne(int id) {
        return this.transactionRepository.readOne(id);
    }

    public void modify(int id, int idMedicine, int idCardClient, int numberOfMedicine, LocalDateTime transactionDate) throws Exception {
        Transaction transaction = new Transaction(id, idMedicine, idCardClient, numberOfMedicine, transactionDate);
        Medicine drug = medicineRepository.readOne(idMedicine);
        if (drug == null) {
            throw new NullException("The drug does not exist");
        }
        CardClient cardClient = cardClientRepository.readOne(idCardClient);
        if (cardClient != null) {
            if (drug.NeedsPrescription()) {
                transaction.setTotalPrice(drug.getPrice() * transaction.getNumberOfMedicine() * 0.85f);
            } else {
                transaction.setTotalPrice(drug.getPrice() * transaction.getNumberOfMedicine() * .90f);
            }
        } else {
            transaction.setTotalPrice(drug.getPrice() * transaction.getNumberOfMedicine());
        }
        this.transactionRepository.update(transaction);
    }

    public void remove(Integer id) {
        this.transactionRepository.delete(id);
    }

    public List<Transaction> showBetweenGivenDates(LocalDate fromDate, LocalDate toDate) {
        List<Transaction> listOfTransactionByDates = new ArrayList<>();
        for (Transaction transaction : this.transactionRepository.readAll()) {
            if ((transaction.getTransactionDate().toLocalDate().isAfter(fromDate) || transaction.getTransactionDate().toLocalDate().isEqual(fromDate))
                    && (transaction.getTransactionDate().toLocalDate().isBefore(toDate) || transaction.getTransactionDate().toLocalDate().isEqual(toDate))) {
                listOfTransactionByDates.add(transaction);
            }
        }
        return listOfTransactionByDates;
    }

    public void deleteBetweenGivenDates(LocalDate fromDate, LocalDate toDate) {
        for (Transaction transaction : this.transactionRepository.readAll()) {
            if ((transaction.getTransactionDate().toLocalDate().isAfter(fromDate) || transaction.getTransactionDate().toLocalDate().isEqual(fromDate))
                    && (transaction.getTransactionDate().toLocalDate().isBefore(toDate) || transaction.getTransactionDate().toLocalDate().isEqual(toDate))) {
                this.transactionRepository.delete(transaction.getId());
            }
        }
    }
}
