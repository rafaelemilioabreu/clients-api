package com.oriontek.model;

public class Address {
    private int addressId;
    private int clientId;
    private String address;

    public Address() {
    }

    public Address(int addressId, int clientId, String address) {
        this.addressId = addressId;
        this.clientId = clientId;
        this.address = address;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}