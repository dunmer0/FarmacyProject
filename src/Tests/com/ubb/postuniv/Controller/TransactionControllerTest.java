package Tests.com.ubb.postuniv.Controller;

import main.com.ubb.postuniv.farmacy.Domain.*;
import main.com.ubb.postuniv.farmacy.Repository.IRepository;
import main.com.ubb.postuniv.farmacy.Repository.Repository;
import main.com.ubb.postuniv.farmacy.Service.ClientController;
import main.com.ubb.postuniv.farmacy.Service.MedicineController;
import main.com.ubb.postuniv.farmacy.Service.TransactionController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TransactionControllerTest {

    IRepository<CardClient> cardClientRepository = new Repository<>();
    IRepository<Medicine> medicineRepository = new Repository<>();
    IRepository<Transaction> transactionRepository = new Repository<>();
    CardClientValidator cardClientValidator = new CardClientValidator();
    MedicineValidator medicineValidator = new MedicineValidator();

    ClientController clientController = new ClientController(cardClientRepository, transactionRepository, medicineRepository, cardClientValidator);
    MedicineController medicineController = new MedicineController(medicineRepository, transactionRepository, medicineValidator);
    TransactionController transactionController = new TransactionController(transactionRepository, medicineRepository, cardClientRepository);

    @BeforeEach
    void setUp() throws DuplicateException, NullException, CardException, MedicineException {
        clientController.add(1,"2345", "Andrei", "Vasile", LocalDate.of(1988,5,24), LocalDate.of(2023,3,1));
        clientController.add(2, "7890", "Catalin", "Ion", LocalDate.of(1990, 6, 5), LocalDate.of(2022, 1, 24));
        clientController.add(3, "12847", "Mihaela", "Popa", LocalDate.of(1989, 11, 5), LocalDate.of(2022, 12, 1));

        medicineController.add(1, "Paracetamol", "Antibiotice", 3.6f, false);
        medicineController.add(2, "Algocalmin", "Pfizer", 1.2f, true);
        medicineController.add(3, "Ampicilina", "Hexypharma", 10.6f, false);

        transactionController.add(1, 1, 0, 3, LocalDateTime.now());
        transactionController.add(2, 2, 1, 1, LocalDateTime.of(2023, 03, 15, 15,30));
        transactionController.add(3, 3, 0, 10, LocalDateTime.now());
        transactionController.add(4, 2, 3, 3, LocalDateTime.of(2023, 03, 12, 15,30));
    }

    @Test
    void addOne() throws DuplicateException, NullException {
        assertEquals(4, transactionController.getAll().size());
        transactionController.add(5, 1, 3, 10, LocalDateTime.now());
        assertEquals(5, transactionController.getAll().size());
    }
    @Test
    void addExistent() {
        assertThrows(DuplicateException.class, ()->
                transactionController.add(2, 1, 3, 10, LocalDateTime.now()));
    }

    @Test
    void getAll() {
        assertEquals(4, transactionController.getAll().size());
        assertNotEquals(5, transactionController.getAll().size());
    }

    @Test
    void getOne() {
        var transaction = transactionController.getOne(2);
        assertInstanceOf(Transaction.class, transaction);
        assertEquals("Transaction{id=2, id_medicine=2, id_client=1, numberOfMedicine=1, transactionDate=15.03.2023 / 15:30, totalPrice= 1.02}",
                transaction.toString());
    }

    @Test
    void modify() throws Exception {
        assertEquals(4, transactionController.getAll().size());
        assertEquals("Transaction{id=2, id_medicine=2, id_client=1, numberOfMedicine=1, transactionDate=15.03.2023 / 15:30, totalPrice= 1.02}",
                transactionController.getOne(2).toString());
        transactionController.modify(2, 3, 2, 10, LocalDateTime.of(2022, 05, 15, 12,30));
        assertEquals(4, transactionController.getAll().size());
        assertEquals("Transaction{id=2, id_medicine=3, id_client=2, numberOfMedicine=10, transactionDate=15.05.2022 / 12:30, totalPrice= 95.40}",
                transactionController.getOne(2).toString());
    }

    @Test
    void removeOne() {
        assertEquals(4, transactionController.getAll().size());
        transactionController.remove(1);
        assertEquals(3, transactionController.getAll().size());
    }

    @Test
    void removeTwo() {
        assertEquals(4, transactionController.getAll().size());
        transactionController.remove(1);
        transactionController.remove(2);
        assertEquals(2, transactionController.getAll().size());
    }

    @Test
    void showBetweenGivenDates() {
        var listOfDates = transactionController.showBetweenGivenDates(LocalDate.of(2023,3,10), LocalDate.of(2023,3,15));
        assertEquals(2, listOfDates.size());
    }

    @Test
    void deleteBetweenGivenDatesOneTransaction() {
        transactionController.deleteBetweenGivenDates(LocalDate.of(2023,3,10), LocalDate.of(2023,3,12));
        assertEquals(3, transactionController.getAll().size());
    }

    @Test
    void deleteBetweenGivenDatesAllTransactions() {
        transactionController.deleteBetweenGivenDates(LocalDate.of(2023,3,10), LocalDate.of(2023,4,12));
        assertEquals(0, transactionController.getAll().size());
    }
}