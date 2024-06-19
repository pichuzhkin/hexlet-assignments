package exercise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import exercise.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // BEGIN
    List<Product> findAllByPrice(int price);
    List<Product> findAllByPriceBetweenOrderByPriceAsc(int priceStart, int priceEnd);
    List<Product> findAllByPriceLessThanEqualOrderByPriceAsc(int priceMax);
    List<Product> findAllByPriceGreaterThanEqualOrderByPriceAsc(int priceMin);
    List<Product> findAll();



    // END
}
