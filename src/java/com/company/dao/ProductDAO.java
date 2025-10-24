package com.company.dao;

import com.company.model.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    public boolean isProductExists(String productId) {
        boolean exists = false;
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM product WHERE productid=?");
            ps.setString(1, productId);
            ResultSet rs = ps.executeQuery();
            exists = rs.next();
        } catch (Exception e) { e.printStackTrace(); }
        return exists;
    }

    public boolean isSupplierExists(String supplierId) {
        boolean exists = false;
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM supplier WHERE supplierid=?");
            ps.setString(1, supplierId);
            ResultSet rs = ps.executeQuery();
            exists = rs.next();
        } catch (Exception e) { e.printStackTrace(); }
        return exists;
    }

    public boolean addProduct(Product product) {
        boolean added = false;
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO product(productid, name, supplierid, stockavailable, openingstock, lastsupplydate, unitprice) VALUES(?,?,?,?,?,?,?)"
            );
            ps.setString(1, product.getProductId());
            ps.setString(2, product.getName());
            ps.setString(3, product.getSupplierId());
            ps.setInt(4, product.getStockAvailable());
            ps.setInt(5, product.getOpeningStock());
            ps.setDate(6, new java.sql.Date(product.getLastSupplyDate().getTime()));
            ps.setDouble(7, product.getUnitPrice());

            int i = ps.executeUpdate();
            if (i > 0) added = true;
        } catch (Exception e) { e.printStackTrace(); }
        return added;
    }

    public Product getProductById(String productId) {
        Product product = null;
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM product WHERE productid=?");
            ps.setString(1, productId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                product = new Product();
                product.setProductId(rs.getString("productid"));
                product.setName(rs.getString("name"));
                product.setSupplierId(rs.getString("supplierid"));
                product.setStockAvailable(rs.getInt("stockavailable"));
                product.setOpeningStock(rs.getInt("openingstock"));
                product.setLastSupplyDate(rs.getDate("lastsupplydate"));
                product.setUnitPrice(rs.getDouble("unitprice"));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return product;
    }

    public boolean updateStock(String productId, int newStock) {
        boolean updated = false;
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("UPDATE product SET stockavailable=? WHERE productid=?");
            ps.setInt(1, newStock);
            ps.setString(2, productId);
            int i = ps.executeUpdate();
            if (i > 0) updated = true;
        } catch (Exception e) { e.printStackTrace(); }
        return updated;
    }

    public List<Product> getLowStockProducts() {
        List<Product> list = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM product WHERE stockavailable < 50");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setProductId(rs.getString("productid"));
                p.setName(rs.getString("name"));
                p.setStockAvailable(rs.getInt("stockavailable"));
                list.add(p);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
}
