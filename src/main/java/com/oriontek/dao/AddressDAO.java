package com.oriontek.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.oriontek.config.DatabaseConnection;
import com.oriontek.model.Address;

public class AddressDAO {

    public List<Address> getAllAddresses() throws SQLException {
        List<Address> addresses = new ArrayList<>();
        String sql = "SELECT * FROM addresses";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                addresses.add(new Address(
                    rs.getInt("address_id"),
                    rs.getInt("client_id"),
                    rs.getString("address")
                ));
            }
        }
        return addresses;
    }
    
    public List<Address> getAddressesByClientId(int clientId) throws SQLException {
        List<Address> addresses = new ArrayList<>();
        String sql = "SELECT * FROM addresses WHERE client_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, clientId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    addresses.add(new Address(
                        rs.getInt("address_id"),
                        rs.getInt("client_id"),
                        rs.getString("address")
                    ));
                }
            }
        }
        return addresses;
    }
    
    public Address getAddressById(int addressId) throws SQLException {
        String sql = "SELECT * FROM addresses WHERE address_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, addressId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Address(
                        rs.getInt("address_id"),
                        rs.getInt("client_id"),
                        rs.getString("address")
                    );
                }
            }
        }
        return null;
    }
    
    public Address createAddress(Address address) throws SQLException {
        String sql = "INSERT INTO addresses (client_id, address) VALUES (?, ?) RETURNING address_id";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, address.getClientId());
            pstmt.setString(2, address.getAddress());
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int newId = rs.getInt(1);
                    address.setAddressId(newId);
                    return address;
                }
            }
        }
        return null;
    }
    
    public boolean updateAddress(Address address) throws SQLException {
        String sql = "UPDATE addresses SET client_id = ?, address = ? WHERE address_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, address.getClientId());
            pstmt.setString(2, address.getAddress());
            pstmt.setInt(3, address.getAddressId());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    public boolean deleteAddress(int addressId) throws SQLException {
        String sql = "DELETE FROM addresses WHERE address_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, addressId);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    public boolean deleteAddressesByClientId(int clientId) throws SQLException {
        String sql = "DELETE FROM addresses WHERE client_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, clientId);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
}