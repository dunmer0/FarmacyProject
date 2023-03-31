package main.com.ubb.postuniv.farmacy.Service;

import main.com.ubb.postuniv.farmacy.Domain.*;
import main.com.ubb.postuniv.farmacy.Repository.IRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MedicineController {
    IRepository<Medicine> medicineRepository;
    IRepository<Transaction> transactionRepository;
    MedicineValidator medicineValidator;

    public MedicineController(IRepository<Medicine> medicineRepository, IRepository<Transaction> transactionRepository, MedicineValidator medicineValidator) {
        this.medicineValidator = medicineValidator;
        this.medicineRepository = medicineRepository;
        this.transactionRepository = transactionRepository;
    }

    public void add(int id, String name, String manufacturer, float price, boolean needsPrescription) throws MedicineException, DuplicateException {
        Medicine medicine = new Medicine(id, name, manufacturer, price, needsPrescription);
        this.medicineValidator.validate(medicine);
        this.medicineRepository.create(medicine);
    }
    public List<Medicine> getAll(){
        return this.medicineRepository.readAll();
    }

    public Medicine getOne(int id){
        return this.medicineRepository.readOne(id);
    }

    public void modify(int id, String name, String manufacturer, float price, boolean needsPrescription) throws MedicineException, DuplicateException {

        Medicine medicine = new Medicine(id, name, manufacturer, price, needsPrescription);
        this.medicineValidator.validate(medicine);
        this.medicineRepository.update(medicine);
    }

    public void remove(int id) {
        this.medicineRepository.delete(id);
    }

    public Medicine getByName(String name) {
        for (Medicine medicine : this.medicineRepository.readAll()) {
            if (medicine.getName().equalsIgnoreCase(name)) {
                return getOne(medicine.getId());
            }
        }
        return null;
    }

    public List<Medicine> getByText(String text) {
        List<Medicine> medsFound = new ArrayList<>();
        for (var drug : this.medicineRepository.readAll()) {
            if (drug.toString().toLowerCase().contains(text.toLowerCase())) {
                medsFound.add(drug);
            }
        }
        return medsFound;
    }

    public void increasePrice(float minPrice, float percentToIncrease) {
        for (Medicine medicine : this.medicineRepository.readAll()) {
            if (medicine.getPrice() < minPrice) {
                medicine.setPrice(medicine.getPrice() + (medicine.getPrice() * percentToIncrease / 100));
            }
        }
    }

    public List<MedicineDTO> medsBySales() {
        List<MedicineDTO> medicineDTOBySales = new ArrayList<>();
        int numberOfSales = 0;
        for (Medicine medicine : this.medicineRepository.readAll()) {
            for (Transaction transaction : this.transactionRepository.readAll()) {
                if (medicine.getId() == transaction.getId_medicine()) {
                    numberOfSales += transaction.getNumberOfMedicine();
                }
            }
            MedicineDTO medicineDTO = new MedicineDTO(medicine, numberOfSales);
            medicineDTOBySales.add(medicineDTO);
            numberOfSales = 0;
        }
        medicineDTOBySales.sort(Collections.reverseOrder());
        return medicineDTOBySales;
    }
}
