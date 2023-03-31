package Tests.com.ubb.postuniv.Controller;

import main.com.ubb.postuniv.farmacy.Domain.*;
import main.com.ubb.postuniv.farmacy.Repository.IRepository;
import main.com.ubb.postuniv.farmacy.Repository.Repository;
import main.com.ubb.postuniv.farmacy.Service.ClientController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ClientControllerTest {
    IRepository<CardClient> cardClientRepository = new Repository<>();
    IRepository<Medicine> medicineRepository = new Repository<>();
    IRepository<Transaction> transactionRepository = new Repository<>();
    CardClientValidator cardClientValidator = new CardClientValidator();
    ClientController clientController = new ClientController(cardClientRepository, transactionRepository, medicineRepository, cardClientValidator);

    @BeforeEach
    void setUp() throws DuplicateException, CardException {
        clientController.add(1,"2345", "Andrei", "Vasile", LocalDate.of(1988,5,24), LocalDate.of(2023,3,1));
        clientController.add(2, "7890", "Catalin", "Ion", LocalDate.of(1990, 6, 5), LocalDate.of(2022, 1, 24));
        clientController.add(3, "12847", "Mihaela", "Popa", LocalDate.of(1989, 11, 5), LocalDate.of(2022, 12, 1));
    }

    @Test
    void add()  {
      assertEquals(3,clientController.getAll().size());
    }
    @Test
    void addThrowsDuplicateException() {
     assertThrows(DuplicateException.class, () ->
             clientController.add(3, "12840", "Mihaela", "Popa", LocalDate.of(1989, 11, 5), LocalDate.of(2022, 12, 1)));
    }
    @Test
    void addThrowsCardException() {
      assertThrows(CardException.class, () ->
              clientController.add(4, "12847", "Mihaela", "Popa", LocalDate.of(1989, 11, 5), LocalDate.of(2022, 12, 1)));
    }

    @Test
    void addThrowsCardExceptionForNullValues() {
        assertThrows(CardException.class, () -> {
            clientController.add(5, "", "", "", null, null);
        });

    }
    @Test
    void getOne() {
        var cardCLient = clientController.getOne(1);
        assertInstanceOf(CardClient.class, cardCLient);
        assertEquals("CardClient{id=1, CNP='2345', firstName='Andrei', lastName='Vasile', dateOfBirth=24.05.1988, dateOfRegistry=01.03.2023}",
                cardCLient.toString());
    }
    @Test
    void getOneNull() {
        var cardClient = clientController.getOne(10);
        assertNull(cardClient);
    }

    @Test
    void modify() throws DuplicateException, CardException {
        clientController.modify(3, "9999", "Izabela", "Popa", LocalDate.of(1989, 11, 5), LocalDate.of(2022, 12, 1));
        assertEquals(3,clientController.getAll().size());
        assertEquals("CardClient{id=3, CNP='9999', firstName='Izabela', lastName='Popa', dateOfBirth=05.11.1989, dateOfRegistry=01.12.2022}",
                clientController.getOne(3).toString());
    }
    @Test
    void modifyNullId(){
        assertThrows(DuplicateException.class, ()->
        clientController.modify(10, "9999", "Izabela", "Popa", LocalDate.of(1989, 11, 5), LocalDate.of(2022, 12, 1)));

    }

    @Test
    void remove() {
        clientController.remove(1);
        assertEquals(2,clientController.getAll().size());
    }

    @Test
    void removeIdDoesNotExist() {
        clientController.remove(10);
        assertEquals(3, clientController.getAll().size());
    }

    @Test
    void getByFullNameReturnsCardCLient() {
        var client = clientController.getByName("Andrei", "Vasile");
        assertEquals("CardClient{id=1, CNP='2345', firstName='Andrei', lastName='Vasile', dateOfBirth=24.05.1988, dateOfRegistry=01.03.2023}",
                client.toString());
    }

    @Test
    void getByFirstNameReturnsNull() {
        var client = clientController.getByName("Andrei", "Ion");
        assertNull(client);
    }

    @Test
    void getByLastNameReturnsNull() {
        var client = clientController.getByName("", "Vasile");
        assertNull(client);
    }

    @Test
    void getByNullNameReturnNull() {
        var client = clientController.getByName("", "");
        assertNull(client);
    }

    @Test
    void getTotalDiscountNumberOfElements() {

        var cardClientDTO = new CardClientDTO(clientController.getOne(3), 0.54f);
        var resultsList = clientController.getTotalDiscount();
        assertEquals(3, resultsList.size());
        assertEquals("CardClientDTO{Card ID 3, total discount 0.54}",
                cardClientDTO.toString());
    }

    @Test
    void getByFullTextReturnsOne() {
        var listOfCards = clientController.getByText("And");
        assertEquals(1, listOfCards.size());
    }

    @Test
    void getByFullTextMultipleReturns() {
        var listOfCards = clientController.getByText("i");
        assertEquals(3, listOfCards.size());
    }

}