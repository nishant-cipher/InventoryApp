package com.company.servlet;

import com.company.dao.ProductDAO;
import com.company.model.Product;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;

public class SalesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");

        out.println("<html><head><title>Make Sale</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; background-color: #f2f2f2; padding: 20px; }");
        out.println("h2 { text-align: center; color: #333; }");
        out.println("form { background-color: #fff; padding: 20px; border-radius: 5px; max-width: 400px; margin: auto; box-shadow: 0 0 10px rgba(0,0,0,0.1); }");
        out.println("label { font-weight: bold; display: block; margin-top: 10px; }");
        out.println("input[type='text'], input[type='number'] { width: 100%; padding: 8px; margin-top: 5px; border: 1px solid #ccc; border-radius: 4px; }");
        out.println("input[type='submit'] { background-color: #2196F3; color: white; padding: 10px 20px; border: none; border-radius: 4px; cursor: pointer; margin-top: 15px; }");
        out.println("input[type='submit']:hover { background-color: #0b7dda; }");
        out.println("a { display: block; margin-top: 20px; text-decoration: none; color: #2196F3; text-align: center; }");
        out.println("a:hover { text-decoration: underline; }");
        out.println("</style>");
        out.println("</head><body>");

        out.println("<h2>Make Sale</h2>");
        out.println("<form method='post'>");

        out.println("<label for='productId'>Product ID:</label>");
        out.println("<input type='text' name='productId' required>");

        out.println("<label for='quantity'>Quantity:</label>");
        out.println("<input type='number' name='quantity' required>");

        out.println("<input type='submit' value='Sell'>");
        out.println("</form>");

        out.println("<a href='LoginServlet'>Back to Home</a>");

        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");

        out.println("<html><head><title>Sale Result</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; background-color: #f9f9f9; padding: 20px; }");
        out.println("h3 { color: #333; text-align: center; }");
        out.println("p { text-align: center; font-size: 16px; }");
        out.println("a { display: inline-block; margin: 10px; text-decoration: none; color: #2196F3; text-align: center; }");
        out.println("a:hover { text-decoration: underline; }");
        out.println("div { text-align: center; margin-top: 50px; }");
        out.println("</style>");
        out.println("</head><body>");
        out.println("<div>");

        String productId = request.getParameter("productId");
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        ProductDAO dao = new ProductDAO();
        Product product = dao.getProductById(productId);

        if (product == null) {
            out.println("<h3>Product not found!</h3>");
        } else if (quantity > product.getStockAvailable()) {
            out.println("<h3>Insufficient stock!</h3>");
        } else {
            int newStock = product.getStockAvailable() - quantity;
            dao.updateStock(productId, newStock);
            double totalPrice = product.getUnitPrice() * quantity;

            out.println("<h3>Sale successful!</h3>");
            out.println("<p>Product: " + product.getName() + "</p>");
            out.println("<p>Quantity: " + quantity + "</p>");
            out.println("<p>Total Price: ₹" + totalPrice + "</p>");
            out.println("<p>Remaining Stock: " + newStock + "</p>");
        }

        out.println("<a href='SalesServlet'>Back</a>");
        out.println("<a href='LoginServlet'>Home</a>");
        out.println("</div>");
        out.println("</body></html>");
    }
}
