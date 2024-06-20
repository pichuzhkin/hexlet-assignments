package exercise.controller;

import java.util.List;

import exercise.dto.ProductCreateDTO;
import exercise.dto.ProductDTO;
import exercise.dto.ProductUpdateDTO;
import exercise.mapper.ProductMapper;
import exercise.mapper.ReferenceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import exercise.exception.ResourceNotFoundException;
import exercise.repository.ProductRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductsController {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;



    // BEGIN
    @GetMapping
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map (p -> productMapper.map(p))
                .toList();
    }

    @GetMapping (path = "/{id}")
    public ProductDTO getProduct(@PathVariable Long id) {
        var entity = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(""));
        return productMapper.map(entity);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO makeProduct(@RequestBody ProductCreateDTO dto) {
        var entity = productMapper.map(dto);
        productRepository.save(entity);
        return productMapper.map(entity);
    }

    @PutMapping(path = "/{id}")
    public ProductDTO fixProduct(@RequestBody ProductUpdateDTO dto, @PathVariable Long id) {
        var entity = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(""));
        productMapper.update(dto,entity);
        productRepository.save(entity);
        return productMapper.map(entity);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void purgeProduct(@PathVariable Long id) {

        productRepository.deleteById(id);

    }

    // END
}
