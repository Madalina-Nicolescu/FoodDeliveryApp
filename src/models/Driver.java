package models;

public class Driver extends Employee  {
    private String carModel;
    private String licensePlate;

    public Driver(String name, Restaurant restaurant, int salary,String carModel, String licensePlate) {
        super(name,restaurant,salary);
        this.carModel = carModel;
        this.licensePlate = licensePlate;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }
}
