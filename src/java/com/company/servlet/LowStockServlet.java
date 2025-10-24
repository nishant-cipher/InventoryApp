package com.company.servlet;

import com.company.dao.ProductDAO;
import com.company.model.Product;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.util.List;

public class LowStockServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");

        ProductDAO dao = new ProductDAO();
        List<Product> lowStockProducts = dao.getLowStockProducts();

        // Old theme CSS
        String cssStyle = "<style>" +
            "body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0; display: flex; justify-content: center; align-items: center; height: 100vh; }" +
            "h2 { color: #333; margin-bottom: 20px; }" +
            "table { width: 80%; margin-top: 20px; border-collapse: collapse; }" +
            "th, td { padding: 10px; text-align: left; border-bottom: 1px solid #ddd; }" +
            "th { background-color: #f2f2f2; font-weight: bold; }" +
            "a { color: #4CAF50; text-decoration: none; font-weight: bold; }" +
            "a:hover { text-decoration: underline; }" +
        "</style>";

        // Output HTML with the table displaying low stock products
        out.println("<html><body>" + cssStyle);
        out.println("<div style='width: 80%; background-color: #fff; padding: 20px; border-radius: 8px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);'>");
        out.println("<h2>Low Stock Products (Stock < 50)</h2>");
        out.println("<table>");
        out.println("<tr><th>Product ID</th><th>Name</th><th>Stock Available</th></tr>");
        for (Product p : lowStockProducts) {
            out.println("<tr><td>" + p.getProductId() + "</td><td>" + p.getName() + "</td><td>" + p.getStockAvailable() + "</td></tr>");
        }
        out.println("</table>");
        out.println("<br><a href='LoginServlet'>Back to Home</a>");
        out.println("</div>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response); // Just forward POST to GET
    }
}

