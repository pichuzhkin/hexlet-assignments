package exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.util.List;

import exercise.repository.ProductRepository;
import exercise.dto.ProductDTO;
import exercise.dto.ProductCreateDTO;
import exercise.dto.ProductUpdateDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.ProductMapper;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductRepository productRepository;


    // BEGIN

    @Autowired
    private ProductMapper productMapper;

    @GetMapping
    public List<ProductDTO> allProducts() {
        return productRepository.findAll().stream().map(p -> productMapper.map(p)).toList();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ProductDTO> someProductById(@PathVariable Long id) {
        var ret = productMapper.map(productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("")));
        return ResponseEntity.status(HttpStatus.OK).body(ret);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ProductDTO makeProduct(@RequestBody ProductCreateDTO incomingDto) {
        var entity = productMapper.map(incomingDto);
        productRepository.save(entity);
        return productMapper.map(entity);
    }

    @PutMapping(path = "/{id}")
    public ProductDTO fixProduct(@RequestBody ProductUpdateDTO incomingDto, @PathVariable Long id) {
        var entity = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(""));
        productMapper.update(incomingDto,entity);
        productRepository.save(entity);
        return productMapper.map(entity);
    }
    // END
}
