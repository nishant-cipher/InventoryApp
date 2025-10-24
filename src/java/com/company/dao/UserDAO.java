package com.company.dao;

import com.company.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    public User validateUser(String userId, String password) {
        User user = null;
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM users WHERE userid = ? AND password = ?"
            );
            ps.setString(1, userId);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setUserId(rs.getString("userid"));
                user.setFullname(rs.getString("fullname"));
                user.setRole(rs.getString("role"));
                user.setPassword(rs.getString("password"));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return user;
    }
}
