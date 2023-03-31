package main.com.ubb.postuniv.farmacy;

import main.com.ubb.postuniv.farmacy.Domain.*;
import main.com.ubb.postuniv.farmacy.Repository.IRepository;
import main.com.ubb.postuniv.farmacy.Repository.Repository;
import main.com.ubb.postuniv.farmacy.Service.ClientController;
import main.com.ubb.postuniv.farmacy.Service.MedicineController;
import main.com.ubb.postuniv.farmacy.Service.TransactionController;
import main.com.ubb.postuniv.farmacy.UI.ClientConsole;
import main.com.ubb.postuniv.farmacy.UI.UI;
import main.com.ubb.postuniv.farmacy.UI.MedicineConsole;
import main.com.ubb.postuniv.farmacy.UI.TransactionConsole;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) throws Exception {
        IRepository<CardClient> cardClientRepository = new Repository<>();
        IRepository<Medicine> medicineRepository = new Repository<>();
        IRepository<Transaction> transactionRepository = new Repository<>();
        CardClientValidator cardClientValidator = new CardClientValidator();
        MedicineValidator medicineValidator = new MedicineValidator();


        ClientController clientController = new ClientController(cardClientRepository, transactionRepository, medicineRepository, cardClientValidator);
        MedicineController medicineController = new MedicineController(medicineRepository, transactionRepository, medicineValidator);
        TransactionController transactionController = new TransactionController(transactionRepository, medicineRepository, cardClientRepository);

        clientController.add(1,"2345", "Andrei", "Vasile", LocalDate.of(1988,5,24), LocalDate.of(2023,3,1));
        clientController.add(2, "7890", "Catalin", "Ion", LocalDate.of(1990, 6, 5), LocalDate.of(2022, 1, 24));
        clientController.add(3, "12847", "Mihaela", "Popa", LocalDate.of(1989, 11, 5), LocalDate.of(2022, 12, 1));
//        clientController.add(3, "12847", "Mihaela", "Popa", LocalDate.of(1989, 11, 5), LocalDate.of(2022, 12, 1));


        medicineController.add(1, "Paracetamol", "Antibiotice", 3.6f, false);
        medicineController.add(2, "Algocalmin", "Pfizer", 1.2f, true);
        medicineController.add(3, "Ampicilina", "Hexypharma", 10.6f, false);

        transactionController.add(1, 1, 0, 3, LocalDateTime.now());
        transactionController.add(2, 2, 1, 1, LocalDateTime.of(2023, 03, 15, 15,30));
        transactionController.add(3, 3, 0, 10, LocalDateTime.now());
        transactionController.add(4, 2, 3, 3, LocalDateTime.of(2023, 03, 12, 15,30));


        ClientConsole clientConsole = new ClientConsole(clientController);
        MedicineConsole medicineConsole = new MedicineConsole(medicineController);
        TransactionConsole transactionConsole = new TransactionConsole(transactionController);

        UI menu = new UI(clientConsole, medicineConsole, transactionConsole);

        menu.runMainMenu();



    }
}