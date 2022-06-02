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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(urlPatterns = "/product/*")
public class ProductServlet extends HttpServlet {

    private ProductRepository productRepository;

    private static final Pattern PARAM_PATTERN = Pattern.compile("\\/(\\d+)");

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

        if (req.getPathInfo() == null || req.getPathInfo().equals("/")) {
            req.setAttribute("products", productRepository.findAll());
            getServletContext().getRequestDispatcher("/product.jsp").forward(req, resp);
        } else{
            Matcher matcher = PARAM_PATTERN.matcher(req.getPathInfo());
            if (matcher.matches()) {
                long id = Long.parseLong(matcher.group(1));
                Product product = this.productRepository.findById(id);
                if (product == null) {
                    resp.getWriter().println("Product not found");
                    resp.setStatus(404);
                    return;
                }
                req.setAttribute("product", product.getProductName());
                getServletContext().getRequestDispatcher("/product_form.jsp").forward(req, resp);
            } else {
                resp.getWriter().println("Bad parameter value");
                resp.setStatus(400);
            }
        }
    }
}
