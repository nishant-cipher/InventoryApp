package com.company.servlet;

import com.company.dao.UserDAO;
import com.company.model.User;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginServlet extends HttpServlet {

    // Handle GET request (direct URL access)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("index.html"); // redirect to login page
    }

    // Handle POST request (form submission)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userid = request.getParameter("userid");
        String password = request.getParameter("password");

        UserDAO userDAO = new UserDAO();
        User user = userDAO.validateUser(userid, password);

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // CSS for consistent theme across login and success/error pages
        String cssStyle = "<style>" +
            "body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0; display: flex; justify-content: center; align-items: center; height: 100vh; }" +
            "h2, h3 { color: #333; margin-bottom: 20px; }" +
            "form { background-color: #fff; padding: 20px; border-radius: 8px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); width: 300px; box-sizing: border-box; }" +
            "label { font-weight: bold; color: #333; margin-bottom: 5px; display: block; }" +
            "input[type='text'], input[type='password'] { width: 100%; padding: 10px; margin: 10px 0 20px 0; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; font-size: 16px; }" +
            "input[type='submit'] { width: 100%; padding: 12px; background-color: #4CAF50; color: white; border: none; border-radius: 4px; cursor: pointer; font-size: 16px; transition: background-color 0.3s ease; }" +
            "input[type='submit']:hover { background-color: #45a049; }" +
            "input[type='text']:focus, input[type='password']:focus { border-color: #4CAF50; outline: none; }" +
            "a { color: #4CAF50; text-decoration: none; font-weight: bold; }" +
            "a:hover { text-decoration: underline; }" +
        "</style>";

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setAttribute("username", user.getFullname());

            // Role-based dashboard
            out.println("<html><body>" + cssStyle);
            out.println("<h2>Welcome " + user.getFullname() + " (" + user.getRole() + ")</h2>");
            switch (user.getRole()) {
                case "DEO":
                    out.println("<a href='AddProductServlet'>Add Product</a><br>");
                    break;
                case "POS":
                    out.println("<a href='SalesServlet'>Make Sale</a><br>");
                    break;
                case "MGR":
                    out.println("<a href='LowStockServlet'>View Low Stock Products</a><br>");
                    break;
            }
            out.println("</body></html>");
        } else {
            out.println("<html><body>" + cssStyle);
            out.println("<h3>Invalid credentials</h3>");
            out.println("<a href='index.html'>Back to Login</a>");
            out.println("</body></html>");
        }
    }
}

