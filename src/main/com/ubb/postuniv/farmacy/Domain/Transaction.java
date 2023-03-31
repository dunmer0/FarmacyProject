package main.com.ubb.postuniv.farmacy.Domain;




import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Transaction extends Entity {

    private int id_medicine;
    private int id_cardClient;
    private int numberOfMedicine;
    private LocalDateTime transactionDate;
    private float totalPrice;


    public Transaction(int id, int id_medicine, int id_cardClient, int numberOfMedicine, LocalDateTime transactionDate) {
        super(id);
        this.id_medicine = id_medicine;
        this.id_cardClient = id_cardClient;
        this.numberOfMedicine = numberOfMedicine;
        this.transactionDate = transactionDate;

    }

    public int getId_medicine() {
        return id_medicine;
    }

    public int getId_CardClient() {
        return id_cardClient;
    }

    public int getNumberOfMedicine() {
        return numberOfMedicine;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + getId() +
                ", id_medicine=" + id_medicine +
                ", id_client=" + id_cardClient +
                ", numberOfMedicine=" + numberOfMedicine +
                ", transactionDate=" + transactionDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy / HH:mm")) +
                String.format(", totalPrice= %.2f", totalPrice) +
                "}";
    }
}


