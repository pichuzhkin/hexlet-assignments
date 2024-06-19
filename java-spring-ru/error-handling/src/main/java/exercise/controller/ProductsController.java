package exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

import exercise.model.Product;
import exercise.repository.ProductRepository;
import exercise.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping(path = "")
    public List<Product> index() {
        return productRepository.findAll();
    }

    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody Product product) {
        return productRepository.save(product);
    }

    // BEGIN
    @GetMapping(path = "/{id}")
    public Product productById(@PathVariable Long id) {
      var product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product with id "+String.valueOf(id)+" not found"));
      return product;
    }

    @PutMapping(path = "/{id}")
    public Product productById(@PathVariable Long id, @RequestBody Product product) {
        var existingProduct = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product with id "+String.valueOf(id)+" not found"));
        existingProduct.setPrice(product.getPrice());
        existingProduct.setTitle(product.getTitle());
        productRepository.save(existingProduct);
        return existingProduct;
    }


    // END

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable long id) {
        productRepository.deleteById(id);
    }
}
