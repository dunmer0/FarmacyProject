package main.com.ubb.postuniv.farmacy.UI;

import main.com.ubb.postuniv.farmacy.Domain.CardClient;
import main.com.ubb.postuniv.farmacy.Domain.CardClientDTO;
import main.com.ubb.postuniv.farmacy.Service.ClientController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ClientConsole {
    ClientController clientController;
    Scanner scanner;

    public ClientConsole(ClientController clientController) {
        this.clientController = clientController;
        this.scanner = new Scanner(System.in).useDelimiter("\n");
    }

    public void runClientConsole() throws Exception {
        while (true) {
            this.showMenu();
            int option = scanner.nextInt();
            switch (option) {
                case 1 -> handleAdd();
                case 2 -> handleReadAll();
                case 3 -> handleReadByName();
                case 4 -> handleUpdate();
                case 5 -> handleDelete();
                case 6 -> handleTotalDiscount();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Please enter a valid command.");
            }
        }
    }

    private void showMenu() {

        System.out.println("Client card menu. For staff only!");
        System.out.println();
        System.out.println("1. Add a client card.");
        System.out.println("2. Read all client cards.");
        System.out.println("3. Find a client card by client name.");
        System.out.println("4. Update a client card.");
        System.out.println("5. Remove a client card.");
        System.out.println("6. Show top of discounts received");
        System.out.println();
        System.out.println("0. Go back to Main Menu.");
        System.out.println();
        System.out.println();
        System.out.println("Please enter an option.");
    }

    private void handleAdd() {
        try {
            System.out.print("Enter id: ");
            int id = scanner.nextInt();
            System.out.print("Enter CNP: ");
            String CNP = scanner.next();
            System.out.print("Enter first name: ");
            String firstName = scanner.next();
            System.out.print("Enter last name: ");
            String lastName = scanner.next();
            System.out.print("Enter the date of birth [dd.MM.yyyy]: ");
            String stringDateOfBirth = scanner.next();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate dateOfBirth = LocalDate.parse(stringDateOfBirth, dtf);
            System.out.print("Enter the date of registry [dd.MM.yyyy]: ");
            String stringDateOfRegistry = scanner.next();
            LocalDate dateOfRegistry = LocalDate.parse(stringDateOfRegistry, dtf);
            this.clientController.add(id, CNP, firstName, lastName, dateOfBirth, dateOfRegistry);
        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
            System.out.println("Do you want to retry? Y/N");
            String choice = scanner.next();
            if (choice.equalsIgnoreCase("Y")) {
                handleAdd();
            }
        }
    }

    private void handleReadAll() {
        for (CardClient cardClient : clientController.getAll()) {
            System.out.println(cardClient);
        }
    }

    private void handleReadByName() {
        System.out.print("Enter first name: ");
        String firstName = scanner.next();
        System.out.print("Enter last name: ");
        String lastName = scanner.next();
        this.clientController.getByName(firstName, lastName);
    }

    private void handleUpdate() {
        System.out.print("Enter the id of card client you want to modify: ");
        int id = scanner.nextInt();
        if (clientController.getOne(id) == null) {
            System.out.println("The card does not exist");
            return;
        }
        try {
            System.out.print("Enter CNP: ");
            String CNP = scanner.next();
            System.out.print("Enter first name: ");
            String firstName = scanner.next();
            System.out.print("Enter last name: ");
            String lastName = scanner.next();
            System.out.print("Enter the date of birth [dd.MM.yyyy]: ");
            String stringDateOfBirth = scanner.next();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate dateOfBirth =  LocalDate.parse(stringDateOfBirth, dtf);
            System.out.print("Enter the date of registry [dd.MM.yyyy]: ");
            String stringDateOfRegistry = scanner.next();
            LocalDate dateOfRegistry =  LocalDate.parse(stringDateOfRegistry, dtf);
            this.clientController.add(id,CNP,firstName,lastName,dateOfBirth,dateOfRegistry);
        }catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Do you want to retry? Y/N");
            String choice = scanner.next();
            if (choice.equalsIgnoreCase("Y")) {
                handleUpdate();
            }
        }
    }

    private void handleDelete() {
        System.out.print("Enter the ID of card client you want to delete: ");
        int id = scanner.nextInt();
        this.clientController.remove(id);
    }

    private void handleTotalDiscount() {
        for (CardClientDTO cardClientDTO : this.clientController.getTotalDiscount()) {
            System.out.println(cardClientDTO);
        }
    }

    public void handleSearchFullText(String text) {
        for (var card : this.clientController.getByText(text)) {
            System.out.println(card);
        }
    }
}
