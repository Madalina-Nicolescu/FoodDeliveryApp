package models;

import java.util.Date;

public class Voucher {
    private int id;
    private String code;
    private int discount;



    public Voucher(int id, String code, int discount) {
        this.id = id;
        this.code = code;
        this.discount = discount;

    }

    public Voucher(int id) {
        this.id = id;
    }

    public Voucher(String code, int discount) {
        this.code = code;
        this.discount = discount;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

}
