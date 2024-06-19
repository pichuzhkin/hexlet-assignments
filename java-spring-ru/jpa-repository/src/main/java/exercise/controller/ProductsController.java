package exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.data.domain.Sort;

import java.util.List;

import exercise.model.Product;
import exercise.repository.ProductRepository;
import exercise.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductRepository productRepository;

    // BEGIN
    @GetMapping(path = "")
    public List<Product> productsByPrice(@RequestParam(name = "min", required = false) Integer priceMin, @RequestParam(name = "max", required = false) Integer priceMax) {
        if (priceMin != null && priceMax != null)
            return productRepository.findAllByPriceBetweenOrderByPriceAsc(priceMin, priceMax);
        else if (priceMin != null)
            return productRepository.findAllByPriceGreaterThanEqualOrderByPriceAsc(priceMin);
        else if (priceMax != null)
            return productRepository.findAllByPriceLessThanEqualOrderByPriceAsc(priceMax);
        else return productRepository.findAll(Sort.by(Sort.Order.asc("price")));
    }
    // END

    @GetMapping(path = "/{id}")
    public Product show(@PathVariable long id) {

        var product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));

        return product;
    }
}
