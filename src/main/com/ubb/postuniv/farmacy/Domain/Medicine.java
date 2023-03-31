package main.com.ubb.postuniv.farmacy.Domain;

public class Medicine extends Entity {

    private String name;
    private String manufacturer;
    private float price;
    private boolean needsPrescription;

    public Medicine(int id, String name, String manufacturer, float price, boolean needsPrescription) {
        super(id);
        this.name = name;
        this.manufacturer = manufacturer;
        this.price = price;
        this.needsPrescription = needsPrescription;

    }

    public String getName() {
        return name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public float getPrice() {
        return price;
    }

    public boolean NeedsPrescription() {
        return needsPrescription;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Medicine{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                String.format("price= %.2f", price) +
                ", needsPrescription=" + needsPrescription +
                '}';
    }
}
