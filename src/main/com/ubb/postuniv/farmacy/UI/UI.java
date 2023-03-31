package main.com.ubb.postuniv.farmacy.UI;

import java.util.Scanner;

public class UI {
    private ClientConsole clientConsole;
    private MedicineConsole medicineConsole;
    private TransactionConsole transactionConsole;
    private Scanner scanner;

    public UI(ClientConsole clientConsole, MedicineConsole medicineConsole, TransactionConsole transactionConsole) {
        this.clientConsole = clientConsole;
        this.medicineConsole = medicineConsole;
        this.transactionConsole = transactionConsole;
        this.scanner = new Scanner(System.in).useDelimiter("\n");
    }

    public void runMainMenu() throws Exception {
        while (true) {
            this.showMenu();
            int option = scanner.nextInt();
            switch (option) {
                case 1 -> handleClientCardMenu();
                case 2 -> handleMedicineMenu();
                case 3 -> handleTransactionMenu();
                case 4 -> handleSearchFullText();

                case 0 -> {
                    return;
                }
                default -> System.out.println("Please enter a valid command.");
            }
        }
    }
    private void showMenu() {
        System.out.println("Shop menu!");
        System.out.println();
        System.out.println("1. Client card menu.");
        System.out.println("2. Medicine menu.");
        System.out.println("3. Transaction menu.");
        System.out.println("4. Search.");
        System.out.println();
        System.out.println("0. EXIT.");
        System.out.println();
        System.out.println("Please enter an option.");
    }

    private void handleClientCardMenu() throws Exception {
        try {
            this.clientConsole.runClientConsole();
        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
        }
    }

    private void handleMedicineMenu() throws Exception {
        this.medicineConsole.runMedicineConsole();
    }

    private void handleTransactionMenu() throws Exception {
        this.transactionConsole.runTransactionConsole();
    }

    private void handleSearchFullText() {
        System.out.println("Please enter text to search.");
        String text = scanner.next();
        this.clientConsole.handleSearchFullText(text);
        this.medicineConsole.handleSearchFullText(text);
    }
}
