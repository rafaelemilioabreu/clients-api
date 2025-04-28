package com.oriontek.model;

public class Address {
    private int addressId;
    private int customerId;
    private String address;

    public Address() {
    }

    public Address(int addressId, int customerId, String address) {
        this.addressId = addressId;
        this.customerId = customerId;
        this.address = address;
    }

    // Getters and Setters
    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}