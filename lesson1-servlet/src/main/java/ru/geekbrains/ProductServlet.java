package ru.geekbrains;


import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/product/*")
public class ProductServlet extends HttpServlet {

    public ProductRepository getProductRepository() {
        return productRepository;
    }

    private ProductRepository productRepository;

    @Override
    public void init() throws ServletException {
        this.productRepository = new ProductRepository();
        productRepository.insert(new Product("product1"));
        productRepository.insert(new Product("product2"));
        productRepository.insert(new Product("product3"));
        productRepository.insert(new Product("product4"));
        productRepository.insert(new Product("product5"));
        productRepository.insert(new Product("product6"));
        productRepository.insert(new Product("product7"));
        productRepository.insert(new Product("product8"));
        productRepository.insert(new Product("product9"));
        productRepository.insert(new Product("product10"));

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter wr = resp.getWriter();
        wr.println("<table>");
        wr.println("<tr>");
        wr.println("<th>ID</th>");
        wr.println("<th>ProductName</th>");
        wr.println("</tr>");




        for (Product product : productRepository.findAll()){

            if(req.getPathInfo() == null || req.getPathInfo().equals("/")){
                wr.println(String.format("<tr><td>%d</td><td>%s</td>", product.getId(), product.getProductName()));
            } else if (req.getPathInfo().equals(String.format("/%d", product.getId()))) {
                wr.println(String.format("<tr><td>%d</td><td>%s</td>", product.getId(), product.getProductName()));
                break;
            }
        }

        wr.println("</table>");

    }
}
