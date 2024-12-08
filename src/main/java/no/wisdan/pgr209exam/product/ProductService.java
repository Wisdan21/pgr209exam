package no.wisdan.pgr209exam.product;

import no.wisdan.pgr209exam.exception.ProductNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductService {
    private final ProductRepo productRepo;

    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public List<Product> findAll() {
        return productRepo.findAll();
    }

    public Product findById(long id) {
        Product product;
        try {
            product = productRepo.findById(id).orElseThrow();
        } catch (NoSuchElementException e) {
            throw new ProductNotFoundException("Product " + id + "not found");
        }
        return product;
    }

    public Product save(Product product) {
        return productRepo.save(product);
    }

    public void deleteById(long id) {
        productRepo.deleteById(id);
    }
}
