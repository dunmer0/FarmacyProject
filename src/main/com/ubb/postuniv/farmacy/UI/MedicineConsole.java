package main.com.ubb.postuniv.farmacy.UI;


import main.com.ubb.postuniv.farmacy.Domain.Medicine;
import main.com.ubb.postuniv.farmacy.Domain.MedicineDTO;
import main.com.ubb.postuniv.farmacy.Service.MedicineController;

import java.util.Scanner;

public class MedicineConsole {
    MedicineController medicineController;
    Scanner scanner;

    public MedicineConsole(MedicineController medicineController) {
        this.medicineController = medicineController;
        this.scanner = new Scanner(System.in).useDelimiter("\n");
    }

    public void runMedicineConsole() {
        while (true) {
            this.showMenu();
            int option = scanner.nextInt();
            switch (option) {
                case 1 -> handleAdd();
                case 2 -> handleReadAll();
                case 3 -> handleReadByName();
                case 4 -> handleUpdate();
                case 5 -> handleDelete();
                case 6 -> handleIncreasePrice();
                case 7 -> handleTopMedicine();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Please enter a valid command.");
            }
        }
    }

    private void showMenu() {
        System.out.println("Medicine menu. For staff only!");
        System.out.println();
        System.out.println("1. Add a client medicine.");
        System.out.println("2. Read all medicine.");
        System.out.println("3. Find a medicine by name.");
        System.out.println("4. Update a medicine.");
        System.out.println("5. Remove a medicine.");
        System.out.println("6. Increase price.");
        System.out.println("7. Show top of medicine.");
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
            System.out.print("Enter medicine name: ");
            String name = scanner.next();
            System.out.print("Enter medicine manufacturer name: ");
            String manufacturer = scanner.next();
            System.out.print("Enter price of medicine: ");
            float price = scanner.nextFloat();
            System.out.print("Needs prescription (yes/no): ");
            String stringNeedsPrescription = scanner.next();
            boolean needsPrescription = stringNeedsPrescription.equalsIgnoreCase("no");
            this.medicineController.add(id, name, manufacturer, price, needsPrescription);
        } catch (Exception exception) {
            System.out.println("Error" + exception.getMessage());
            System.out.println("Do you want to retry? Y/N");
            String choice = scanner.next();
            if (choice.equalsIgnoreCase("Y")) {
                handleAdd();
            }
        }

    }

    private void handleReadAll() {
        for (Medicine medicine : medicineController.getAll()) {
            System.out.println(medicine);
        }
    }

    private void handleReadByName() {
        System.out.print("Enter name: ");
        String firstName = scanner.next();

        this.medicineController.getByName(firstName);
    }

    private void handleUpdate() {
        System.out.print("Enter the id of medicine you want to modify: ");
        int id = scanner.nextInt();
        if (medicineController.getOne(id) == null) {
            System.out.println("The card does not exist");
            return;
        }
        try {
            System.out.print("Enter id: ");
            id = scanner.nextInt();
            System.out.print("Enter medicine name: ");
            String name = scanner.next();
            System.out.print("Enter medicine manufacturer name: ");
            String manufacturer = scanner.next();
            System.out.print("Enter price of medicine: ");
            float price = scanner.nextFloat();
            System.out.print("Needs prescription (yes/no): ");
            String stringNeedsPrescription = scanner.next();
            boolean needsPrescription = stringNeedsPrescription.equalsIgnoreCase("no");
            this.medicineController.add(id, name, manufacturer, price, needsPrescription);
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
            System.out.println("Do you want to retry? Y/N");
            String choice = scanner.next();
            if (choice.equalsIgnoreCase("Y")) {
                handleUpdate();
            }
        }
    }
    private void handleDelete() {
        System.out.print("Enter the ID the medicine you want to delete: ");
        int id = scanner.nextInt();
        this.medicineController.remove(id);
    }

    private void handleIncreasePrice() {
        try {
            System.out.print("Specify the minimum price:");
            float minPrice = scanner.nextFloat();
            System.out.println();
            System.out.print("Specify the percent:");
            float percent = scanner.nextFloat();
            System.out.println();
            this.medicineController.increasePrice(minPrice, percent);
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
    }

    private void handleTopMedicine() {
        for (MedicineDTO med : this.medicineController.medsBySales()) {
            System.out.println(med);
        }
    }
    public void handleSearchFullText(String text) {
        for (var drug : this.medicineController.getByText(text)) {
            System.out.println(drug);
        }
    }

}
