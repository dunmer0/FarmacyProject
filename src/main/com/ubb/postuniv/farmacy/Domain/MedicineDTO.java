package main.com.ubb.postuniv.farmacy.Domain;

import org.jetbrains.annotations.NotNull;

public record MedicineDTO(Medicine medicine, Integer salesNumber) implements Comparable<MedicineDTO> {


    @Override
    public int compareTo(@NotNull MedicineDTO o) {
        return this.salesNumber.compareTo(o.salesNumber);
    }

    @Override
    public String toString() {
        return "MedicineDTO{" +
                String.format("id %d-%s, total sold= %d.}",
                        this.medicine.getId(), this.medicine.getName(), this.salesNumber)
               ;
    }
}
