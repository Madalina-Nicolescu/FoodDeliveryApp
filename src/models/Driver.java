package models;

public class Driver extends Employee  {
    private String carModel;
    private String licensePlate;


    public Driver(String name, int salary,String phone,String carModel, String licensePlate) {
        super(name,salary,phone);
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
