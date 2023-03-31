package main.com.ubb.postuniv.farmacy.Domain;

import main.com.ubb.postuniv.farmacy.Repository.IRepository;

public class CardClientValidator {

    public void validate(CardClient cardClientToValidate, IRepository<CardClient> cardClientRepository) throws CardException {
        StringBuilder sb = new StringBuilder();

        if (cardClientToValidate.getCNP().isEmpty()) {
            sb.append("CNP cannot be empty.");
        }
        for (CardClient cardClient : cardClientRepository.readAll()) {
            if (cardClientToValidate.getCNP().equalsIgnoreCase(cardClient.getCNP())) {
                sb.append("CNP must be unique!!");
            }
        }
        if (cardClientToValidate.getFirstName().isEmpty()) {
            sb.append("First name must not be empty.");
        }
        if (cardClientToValidate.getLastName().isEmpty()) {
            sb.append("Last name must not be empty.");
        }
        if (cardClientToValidate.getDateOfBirth() == null) {
            sb.append("Date of birth must not be empty.");
        }
        if (cardClientToValidate.getDateOfRegistry() == null) {
            sb.append("Date of registry must not be empty.");
        }
        if (sb.length() > 0) {
            throw new CardException(sb.toString());
        }



    }
}
