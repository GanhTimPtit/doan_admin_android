package com.example.king.managebook.model.response;

public class CustomerStatictisPreView {
    private String id;
    private String name;
    private int totalRate;
    private int totalBill;
    private long totalPrice;

    public CustomerStatictisPreView() {
    }

    public CustomerStatictisPreView(String id, String name, int totalRate, int totalBill, long totalPrice) {
        this.id = id;
        this.name = name;
        this.totalRate = totalRate;
        this.totalBill = totalBill;
        this.totalPrice = totalPrice;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalRate() {
        return totalRate;
    }

    public void setTotalRate(int totalRate) {
        this.totalRate = totalRate;
    }

    public int getTotalBill() {
        return totalBill;
    }

    public void setTotalBill(int totalBill) {
        this.totalBill = totalBill;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }
}
