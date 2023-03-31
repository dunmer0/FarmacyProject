package main.com.ubb.postuniv.farmacy.Domain;

import org.jetbrains.annotations.NotNull;

public record CardClientDTO(CardClient cardClient, Float totalDiscount) implements Comparable<CardClientDTO> {

    @Override
    public int compareTo(@NotNull CardClientDTO o) {
        return this.totalDiscount.compareTo(o.totalDiscount);
    }

    @Override
    public String toString() {
        return "CardClientDTO{" +
                String.format("Card ID %d, " +
                        "total discount %.2f", cardClient.getId(), totalDiscount) +
                '}';
    }
}
