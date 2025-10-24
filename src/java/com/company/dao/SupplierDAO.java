package com.company.dao;

import com.company.model.Supplier;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SupplierDAO {

    public Supplier getSupplierById(String supplierId) {
        Supplier supplier = null;
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM supplier WHERE supplierid=?");
            ps.setString(1, supplierId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                supplier = new Supplier();
                supplier.setSupplierId(rs.getString("supplierid"));
                supplier.setName(rs.getString("name"));
                supplier.setContact(rs.getString("contact"));
                supplier.setEmail(rs.getString("email"));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return supplier;
    }
}
