package com.company.servlet;

import com.company.dao.ProductDAO;
import com.company.model.Product;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");

        out.println("<html><head><title>Add Product</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; background-color: #f2f2f2; padding: 20px; }");
        out.println("h2 { text-align: center; color: #333; }");
        out.println("form { background-color: #fff; padding: 20px; border-radius: 5px; max-width: 400px; margin: auto; box-shadow: 0 0 10px rgba(0,0,0,0.1); }");
        out.println("label { font-weight: bold; display: block; margin-top: 10px; }");
        out.println("input[type='text'], input[type='number'] { width: 100%; padding: 8px; margin-top: 5px; border: 1px solid #ccc; border-radius: 4px; }");
        out.println("input[type='submit'] { background-color: #4CAF50; color: white; padding: 10px 20px; border: none; border-radius: 4px; cursor: pointer; margin-top: 15px; }");
        out.println("input[type='submit']:hover { background-color: #45a049; }");
        out.println("a { display: inline-block; margin-top: 20px; text-decoration: none; color: #4CAF50; text-align: center; display: block; }");
        out.println("a:hover { text-decoration: underline; }");
        out.println("</style>");
        out.println("</head><body>");

        out.println("<h2>Add Product</h2>");
        out.println("<form method='post'>");
        out.println("<label for='productId'>Product ID:</label>");
        out.println("<input type='text' name='productId' required>");

        out.println("<label for='name'>Name:</label>");
        out.println("<input type='text' name='name' required>");

        out.println("<label for='supplierId'>Supplier ID:</label>");
        out.println("<input type='text' name='supplierId' required>");

        out.println("<label for='stock'>Stock Available:</label>");
        out.println("<input type='number' name='stock' required>");

        out.println("<label for='price'>Unit Price:</label>");
        out.println("<input type='number' step='0.01' name='price' required>");

        out.println("<label for='date'>Last Supply Date (yyyy-MM-dd):</label>");
        out.println("<input type='text' name='date' required>");

        out.println("<input type='submit' value='Add Product'>");
        out.println("</form>");

        out.println("<a href='LoginServlet'>Back to Home</a>");

        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");

        out.println("<html><head><title>Product Submission</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; background-color: #f9f9f9; padding: 20px; }");
        out.println("h3 { color: #333; text-align: center; }");
        out.println("a { display: inline-block; margin: 10px; text-decoration: none; color: #4CAF50; }");
        out.println("a:hover { text-decoration: underline; }");
        out.println("div { text-align: center; margin-top: 50px; }");
        out.println("</style>");
        out.println("</head><body>");
        out.println("<div>");

        String productId = request.getParameter("productId");
        String name = request.getParameter("name");
        String supplierId = request.getParameter("supplierId");
        int stock = Integer.parseInt(request.getParameter("stock"));
        double price = Double.parseDouble(request.getParameter("price"));
        String dateStr = request.getParameter("date");

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date lastSupplyDate = sdf.parse(dateStr);

            ProductDAO dao = new ProductDAO();

            if (dao.isProductExists(productId)) {
                out.println("<h3>Product ID already exists!</h3>");
            } else if (!dao.isSupplierExists(supplierId)) {
                out.println("<h3>Supplier ID does not exist!</h3>");
            } else {
                Product product = new Product();
                product.setProductId(productId);
                product.setName(name);
                product.setSupplierId(supplierId);
                product.setStockAvailable(stock);
                product.setOpeningStock(stock);
                product.setUnitPrice(price);
                product.setLastSupplyDate(lastSupplyDate);

                boolean added = dao.addProduct(product);
                if (added)
                    out.println("<h3>Product added successfully!</h3>");
                else
                    out.println("<h3>Error adding product!</h3>");
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h3>Invalid data or server error.</h3>");
        }

        out.println("<a href='AddProductServlet'>Back</a>");
        out.println("<a href='LoginServlet'>Home</a>");
        out.println("</div>");
        out.println("</body></html>");
    }
}
