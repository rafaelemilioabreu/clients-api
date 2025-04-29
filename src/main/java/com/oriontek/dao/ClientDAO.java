package com.oriontek.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.oriontek.config.DatabaseConnection;
import com.oriontek.model.Client;
import com.oriontek.model.Address;

public class ClientDAO {
    
    private AddressDAO addressDAO = new AddressDAO();
    
    public List<Client> getAllClients() throws SQLException {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM clients";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Client client = new Client(
                    rs.getInt("client_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("email"),
                    rs.getString("phone")
                );
                
                client.setAddresses(addressDAO.getAddressesByClientId(client.getClientId()));
                clients.add(client);
            }
        }
        return clients;
    }
    
    public Client getClientById(int clientId) throws SQLException {
        String sql = "SELECT * FROM clients WHERE client_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, clientId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Client client = new Client(
                        rs.getInt("client_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("phone")
                    );
                    
                    client.setAddresses(addressDAO.getAddressesByClientId(clientId));
                    return client;
                }
            }
        }
        return null;
    }
    
    public Client createClient(Client client) throws SQLException {
        String sql = "INSERT INTO clients (first_name, last_name, email, phone) VALUES (?, ?, ?, ?) RETURNING client_id";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, client.getFirstName());
            pstmt.setString(2, client.getLastName());
            pstmt.setString(3, client.getEmail());
            pstmt.setString(4, client.getPhone());
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int newId = rs.getInt(1);
                    client.setClientId(newId);
                    
                    if (client.getAddresses() != null && !client.getAddresses().isEmpty()) {
                        for (Address address : client.getAddresses()) {
                            address.setClientId(newId);
                            addressDAO.createAddress(address);
                        }
                    }
                    return client;
                }
            }
        }
        return null;
    }
    
    public boolean updateClient(Client client) throws SQLException {
        String sql = "UPDATE clients SET first_name = ?, last_name = ?, email = ?, phone = ? WHERE client_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, client.getFirstName());
            pstmt.setString(2, client.getLastName());
            pstmt.setString(3, client.getEmail());
            pstmt.setString(4, client.getPhone());
            pstmt.setInt(5, client.getClientId());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    public boolean deleteClient(int clientId) throws SQLException {
        String sql = "DELETE FROM clients WHERE client_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, clientId);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
}