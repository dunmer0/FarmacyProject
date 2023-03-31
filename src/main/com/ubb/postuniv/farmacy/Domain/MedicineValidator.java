package main.com.ubb.postuniv.farmacy.Domain;

public class MedicineValidator {

    public void validate(Medicine medicine) throws MedicineException {
        StringBuilder sb = new StringBuilder();

        if (medicine.getName().isEmpty()) {
            sb.append("Name must not be empty.");
        }
        if (medicine.getManufacturer().isEmpty()) {
            sb.append("Manufacturer must not be empty.");
        }
        if (medicine.getPrice() < 0) {
            sb.append("Price must be higher than 0.");
        }
        if (!sb.isEmpty()) {
            throw new MedicineException(sb.toString());
        }
    }
}
