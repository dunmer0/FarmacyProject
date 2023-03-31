package Tests.com.ubb.postuniv.Controller;

import main.com.ubb.postuniv.farmacy.Domain.*;
import main.com.ubb.postuniv.farmacy.Repository.IRepository;
import main.com.ubb.postuniv.farmacy.Repository.Repository;
import main.com.ubb.postuniv.farmacy.Service.MedicineController;
import main.com.ubb.postuniv.farmacy.Service.TransactionController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MedicineControllerTest {
    IRepository<CardClient> cardClientRepository = new Repository<>();
    IRepository<Medicine> medicineRepository = new Repository<>();
    IRepository<Transaction> transactionRepository = new Repository<>();
    CardClientValidator cardClientValidator = new CardClientValidator();
    MedicineValidator medicineValidator = new MedicineValidator();
    MedicineController medicineController = new MedicineController(medicineRepository, transactionRepository, medicineValidator);
    TransactionController transactionController = new TransactionController(transactionRepository, medicineRepository, cardClientRepository);

    @BeforeEach
    void setUp() throws DuplicateException, MedicineException, NullException {
        medicineController.add(1, "Paracetamol", "Antibiotice", 3.6f, false);
        medicineController.add(2, "Algocalmin", "Pfizer", 1.2f, true);
        medicineController.add(3, "Ampicilina", "Hexypharma", 10.6f, false);


    }

    @Test
    void add() throws DuplicateException, MedicineException {
        medicineController.add(4, "Test", "Ant.Iasi", 5.4f, true);

        assertEquals(4,medicineController.getAll().size());
    }

    @Test
    void addThrowsDuplicateException() {
        assertThrows(DuplicateException.class, () -> {
            medicineController.add(3, "Test", "Ant.Iasi", 5.4f, true);
        });
    }

    @Test
    void addThrowsMedicineExceptionName() {
        assertThrows(MedicineException.class, () ->
                medicineController.add(4, "", "Ant.Iasi", 5.4f, true));
    }

    @Test
    void addThrowsMedicineExceptionManufacturer() {
        assertThrows(MedicineException.class, () ->
            medicineController.add(5, "Test", "", 5.4f, true));
    }

    @Test
    void addThroesMedicineExceptionPrice() {
        assertThrows(MedicineException.class, ()->
                medicineController.add(5, "Test", "", -5.4f, true));
    }
    @Test
    void getAll() {
        assertEquals(3,medicineController.getAll().size());
    }

    @Test
    void getAllWithAddedMedicine() throws DuplicateException, MedicineException {
        medicineController.add(4, "Test4", "ANtibiotuce", 999f, true);
        medicineController.add(5, "Test5", "Plafar", 100f, false);
        medicineController.add(6, "Test6", "Placebo", 10.6f, true);
      assertEquals(6, medicineController.getAll().size());
    }

    @Test
    void getOneFirst() {
        var drug = medicineController.getOne(1);
        assertInstanceOf(Medicine.class, drug);
        assertEquals("Medicine{id=1, name='Paracetamol', manufacturer='Antibiotice'price= 3.60, needsPrescription=false}",
                drug.toString());
    }

    @Test
    void getOneSecond() {
        var drug = medicineController.getOne(2);
        assertInstanceOf(Medicine.class, drug );
        assertEquals("Medicine{id=2, name='Algocalmin', manufacturer='Pfizer'price= 1.20, needsPrescription=true}",
                drug.toString());
    }

    @Test
    void modify() throws DuplicateException, MedicineException {
        assertEquals(3, medicineController.getAll().size());
        medicineController.modify(1, "ACC", "Pfizer", 50.89f, true);
        assertEquals(3, medicineController.getAll().size());
        assertEquals("Medicine{id=1, name='ACC', manufacturer='Pfizer'price= 50.89, needsPrescription=true}",
                medicineController.getOne(1).toString());
    }

    @Test
    void modifyInexistentId() {
        assertThrows(DuplicateException.class, ()->
                medicineController.modify(6, "ACC", "Pfizer", 50.89f, true));
    }

    @Test
    void removeFirst() {
        assertEquals(3, medicineController.getAll().size());
        medicineController.remove(1);
        assertEquals(2, medicineController.getAll().size());
    }
    @Test
    void removeThird() {
        assertEquals(3, medicineController.getAll().size());
        medicineController.remove(3);
        assertEquals(2, medicineController.getAll().size());
    }
    @Test
    void removeFirstAndSecond() {
        assertEquals(3, medicineController.getAll().size());
        medicineController.remove(1);
        assertEquals(2, medicineController.getAll().size());
        medicineController.remove(2);
        assertEquals(1, medicineController.getAll().size());
    }

    @Test
    void removeNotPresent() {
        assertEquals(3, medicineController.getAll().size());
        medicineController.remove(4);
        assertEquals(3,medicineController.getAll().size());
    }


    @Test
    void getByNameThatExistsReturnsMedicineObject() {
        var drug = medicineController.getByName("Paracetamol");
        assertEquals("Medicine{id=1, name='Paracetamol', manufacturer='Antibiotice'price= 3.60, needsPrescription=false}",
                drug.toString());
    }

    @Test
    void getByNameThatDoesNotExistReturnsNull() {
        var drug = medicineController.getByName("notPresent");
        assertNull(drug);
    }

    @Test
    void getByTextOneObject() {
        var listOfMeds = medicineController.getByText("Para");
        assertEquals(1, listOfMeds.size());
        assertEquals("Medicine{id=1, name='Paracetamol', manufacturer='Antibiotice'price= 3.60, needsPrescription=false}",
                listOfMeds.get(0).toString());
    }

    @Test
    void getByTestMultiple() {
        var listOfMeds = medicineController.getByText("false");
        assertEquals(2, listOfMeds.size());
        assertEquals("Medicine{id=1, name='Paracetamol', manufacturer='Antibiotice'price= 3.60, needsPrescription=false}",
                listOfMeds.get(0).toString());
        assertEquals("Medicine{id=3, name='Ampicilina', manufacturer='Hexypharma'price= 10.60, needsPrescription=false}",
                listOfMeds.get(1).toString());
    }

    @Test
    void increasePriceByOne() {
        var drug = medicineController.getOne(2);
        assertEquals("Medicine{id=2, name='Algocalmin', manufacturer='Pfizer'price= 1.20, needsPrescription=true}",
                drug.toString());
        medicineController.increasePrice(1.6f,50f);
        drug = medicineController.getOne(2);
        assertEquals("Medicine{id=2, name='Algocalmin', manufacturer='Pfizer'price= 1.80, needsPrescription=true}",
                drug.toString());
    }

    @Test
    void increasePriceAll() {
        assertEquals("Medicine{id=1, name='Paracetamol', manufacturer='Antibiotice'price= 3.60, needsPrescription=false}", medicineController.getOne(1).toString());
        assertEquals("Medicine{id=2, name='Algocalmin', manufacturer='Pfizer'price= 1.20, needsPrescription=true}", medicineController.getOne(2).toString());
        assertEquals("Medicine{id=3, name='Ampicilina', manufacturer='Hexypharma'price= 10.60, needsPrescription=false}", medicineController.getOne(3).toString());
        medicineController.increasePrice(15, 50);
        assertEquals("Medicine{id=1, name='Paracetamol', manufacturer='Antibiotice'price= 5.40, needsPrescription=false}", medicineController.getOne(1).toString());
        assertEquals("Medicine{id=2, name='Algocalmin', manufacturer='Pfizer'price= 1.80, needsPrescription=true}", medicineController.getOne(2).toString());
        assertEquals("Medicine{id=3, name='Ampicilina', manufacturer='Hexypharma'price= 15.90, needsPrescription=false}", medicineController.getOne(3).toString());
    }

    @Test
    void medsBySales() throws DuplicateException, NullException {
        transactionController.add(1, 1, 0, 3, LocalDateTime.now());
        transactionController.add(2, 2, 1, 1, LocalDateTime.of(2023, 03, 15, 15,30));
        transactionController.add(3, 3, 0, 10, LocalDateTime.now());
        transactionController.add(4, 2, 3, 3, LocalDateTime.of(2023, 03, 12, 15,30));
        var listOfMedsSold = medicineController.medsBySales();
        assertInstanceOf(MedicineDTO.class, listOfMedsSold.get(1));
        assertEquals(3, listOfMedsSold.size());
        assertEquals("MedicineDTO{id 3-Ampicilina, total sold= 10.}", listOfMedsSold.get(0).toString());
        assertEquals("MedicineDTO{id 2-Algocalmin, total sold= 4.}", listOfMedsSold.get(1).toString());
        assertEquals("MedicineDTO{id 1-Paracetamol, total sold= 3.}", listOfMedsSold.get(2).toString());
    }
}