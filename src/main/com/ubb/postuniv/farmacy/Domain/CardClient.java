package main.com.ubb.postuniv.farmacy.Domain;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CardClient extends Entity {
    final private String CNP;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private LocalDate dateOfRegistry;

    public CardClient(int id, String CNP, String firstName, String lastName, LocalDate dateOfBirth, LocalDate dateOfRegistry) {
        super(id);
        this.CNP = CNP;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.dateOfRegistry = dateOfRegistry;
    }

    public String getCNP() {
        return CNP;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public LocalDate getDateOfRegistry() {
        return dateOfRegistry;
    }



    @Override
    public String toString() {
        return "CardClient{" +
                "id=" +  getId()+
                ", CNP='" + CNP + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) +
                ", dateOfRegistry=" + dateOfRegistry.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) +
                '}';
    }
}