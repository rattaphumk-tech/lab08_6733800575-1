package com.example.demo.service;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.strategy.DiscountContext;
import com.example.demo.strategy.DiscountStrategy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();
        DiscountContext context = new DiscountContext();
        
        for (Product product : products) {
            DiscountStrategy strategy = DiscountContext.getStrategyByType(product.getDiscountType());
            context.setStrategy(strategy);
            product.setFinalPrice(context.calculate(product.getPrice()));
        }
        return products;
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id).map(product -> {
            DiscountContext context = new DiscountContext();
            DiscountStrategy strategy = DiscountContext.getStrategyByType(product.getDiscountType());
            context.setStrategy(strategy);
            product.setFinalPrice(context.calculate(product.getPrice()));
            return product;
        });
    }

    public Product saveProduct(Product product) {
        if (product.getDetail() != null) {
            product.getDetail().setProduct(product);
        }
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}