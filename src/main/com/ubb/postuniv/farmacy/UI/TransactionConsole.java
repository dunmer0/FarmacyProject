package main.com.ubb.postuniv.farmacy.UI;

import main.com.ubb.postuniv.farmacy.Domain.DuplicateException;
import main.com.ubb.postuniv.farmacy.Domain.NullException;
import main.com.ubb.postuniv.farmacy.Domain.Transaction;
import main.com.ubb.postuniv.farmacy.Service.TransactionController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class TransactionConsole {
    private final TransactionController transactionController;
    private final Scanner scanner;

    public TransactionConsole(TransactionController transactionController) {
        this.transactionController = transactionController;
        this.scanner = new Scanner(System.in).useDelimiter("\n");
    }

    public void runTransactionConsole() {
        while (true) {
            this.showMenu();
            int option = scanner.nextInt();
            switch (option) {
                case 1 -> handleAdd();
                case 2 -> handleReadAll();
                case 3 -> handleDelete();
                case 4 -> handleReadBetweenDates();
                case 5 -> handleDeleteBetweenDates();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Please enter a valid command.");
            }
        }
    }


    private void showMenu() {
        System.out.println("Transaction handle menu. For staff only!");
        System.out.println();
        System.out.println("1. Add a transaction.");
        System.out.println("2. Read all transactions.");
        System.out.println("3. Delete a transaction");
        System.out.println("4. Show all transactions between dates.");
        System.out.println("5. Delete transactions between dates.");
        System.out.println();
        System.out.println("0. Go back to Main Menu.");
        System.out.println();
        System.out.println();
        System.out.println("Please enter an option.");
        System.out.println();
    }

    private void handleAdd() {
        try {
            System.out.print("Enter id: ");
            int id = scanner.nextInt();
            System.out.print("Enter medicine id: ");
            int id_medicine = scanner.nextInt();
            System.out.print("Enter card client id: ");
            int id_cardClient = scanner.nextInt();
            System.out.print("Enter number of medicine: ");
            int numberOfMedicine = scanner.nextInt();
            LocalDateTime transactionDate = LocalDateTime.now();
            this.transactionController.add(id, id_medicine, id_cardClient, numberOfMedicine, transactionDate);
        } catch (DuplicateException | NullException exception) {
            System.out.println("Error " + exception.getMessage());
            System.out.println("Do you want to retry? Y/N");
            String choice = scanner.next();
            if (choice.equalsIgnoreCase("Y")) {
                handleAdd();
            }
        }

    }

    private void handleReadAll() {
        for (Transaction transaction : transactionController.getAll()) {
            System.out.println(transaction);
        }
    }

    private void handleDelete() {
        System.out.print("Enter the ID the medicine you want to delete: ");
        int id = scanner.nextInt();
        this.transactionController.remove(id);
    }

    private void handleReadBetweenDates()  {
        try {
            System.out.print("Please enter the first date of the interval [dd.MM.yyyy]:");
            String stringFromDate = scanner.next();
            System.out.println();
            System.out.print("Please enter the second date of the interval [dd.MM.yyyy]:");
            String stringToDate = scanner.next();
            System.out.println();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate fromDate = LocalDate.parse(stringFromDate, dtf);
            LocalDate toDate = LocalDate.parse(stringToDate, dtf);
            for (var transaction : this.transactionController.showBetweenGivenDates(fromDate, toDate)) {
                System.out.println(transaction);
            };
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Do you want to try again? Y/N");
            String choice = scanner.next();
            if (choice.equalsIgnoreCase("Y")) {
                handleReadBetweenDates();
            }
        }
    }
    private void handleDeleteBetweenDates() {
        try {
            System.out.print("Please enter the first date of the interval [dd.MM.yyyy]:");
            String stringFromDate = scanner.next();
            System.out.println();
            System.out.print("Please enter the second date of the interval [dd.MM.yyyy]:");
            String stringToDate = scanner.next();
            System.out.println();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate fromDate = LocalDate.parse(stringFromDate, dtf);
            LocalDate toDate = LocalDate.parse(stringToDate, dtf);
            this.transactionController.deleteBetweenGivenDates(fromDate, toDate);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Do you want to try again? Y/N");
            String choice = scanner.next();
            if (choice.equalsIgnoreCase("Y")) {
                handleDeleteBetweenDates();
            }
        }
    }
}
