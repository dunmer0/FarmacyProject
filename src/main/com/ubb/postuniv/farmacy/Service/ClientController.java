package main.com.ubb.postuniv.farmacy.Service;
import main.com.ubb.postuniv.farmacy.Domain.*;
import main.com.ubb.postuniv.farmacy.Repository.IRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ClientController {
    private  IRepository<CardClient> clientRepository;
    private  IRepository<Transaction> transactionRepository;
    private  IRepository<Medicine> medicineRepository;
    private  CardClientValidator cardClientValidator;
    public ClientController(IRepository<CardClient> clientRepository, IRepository<Transaction> transactionRepository, IRepository<Medicine> medicineRepository, CardClientValidator cardClientValidator) {
        this.medicineRepository = medicineRepository;
        this.transactionRepository = transactionRepository;
        this.clientRepository = clientRepository;
        this.cardClientValidator = cardClientValidator;
    }
    public void add(int id, String CNP, String firstName, String lastName, LocalDate dateOfBirth, LocalDate dateOfRegistry) throws CardException, DuplicateException {
        CardClient cardClient = new CardClient(id, CNP, firstName, lastName, dateOfBirth, dateOfRegistry);
        this.cardClientValidator.validate(cardClient, clientRepository);
        this.clientRepository.create(cardClient);
    }
    public List<CardClient> getAll() {
        return this.clientRepository.readAll();
    }
    public CardClient getOne(int id) {
        return this.clientRepository.readOne(id);
    }
    public void modify(int id, String CNP, String firstName, String lastName, LocalDate dateOfBirth, LocalDate dateOfRegistry) throws CardException, DuplicateException {
        CardClient cardClient = new CardClient(id, CNP, firstName, lastName, dateOfBirth, dateOfRegistry);
        this.cardClientValidator.validate(cardClient, clientRepository);
        this.clientRepository.update(cardClient);
    }
    public void remove(int id) {
        this.clientRepository.delete(id);
    }
    public CardClient getByName(String firstName, String lastName) {
        for (CardClient cardClient : this.clientRepository.readAll()) {
            if (cardClient.getFirstName().equalsIgnoreCase(firstName)
                    && cardClient.getLastName().equalsIgnoreCase(lastName)) {
               return getOne(cardClient.getId());
            }
        }
        return null;
    }

    public List<CardClient> getByText(String text) {
        List<CardClient> cardsFound = new ArrayList<>();
        for (var card : this.clientRepository.readAll()) {
            if (card.toString().toLowerCase().contains(text.toLowerCase())) {
                cardsFound.add(card);
            }
        }
        return cardsFound;
    }

    public List<CardClientDTO> getTotalDiscount() {
        List<CardClientDTO> clientTotalDiscount = new ArrayList<>();
        float totalDiscount = 0;
        for (CardClient cardClient : this.clientRepository.readAll()) {
            for (Transaction transaction : this.transactionRepository.readAll()) {
                if (cardClient.getId() == transaction.getId_CardClient()) {
                    totalDiscount += this.medicineRepository.readOne(transaction.getId_medicine()).getPrice() * transaction.getNumberOfMedicine() - transaction.getTotalPrice();
                    }
                }
            CardClientDTO cardClientDTO = new CardClientDTO(cardClient, totalDiscount);
            clientTotalDiscount.add(cardClientDTO);
            totalDiscount = 0;
            }
        clientTotalDiscount.sort(Collections.reverseOrder());
        return clientTotalDiscount;
    }
}
