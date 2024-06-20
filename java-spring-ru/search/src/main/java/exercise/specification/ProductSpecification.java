package exercise.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import exercise.dto.ProductParamsDTO;
import exercise.model.Product;

// BEGIN
@Component // Для возможности автоматической инъекции
public class ProductSpecification {
    // Генерация спецификации на основе параметров внутри DTO
    // Для удобства каждый фильтр вынесен в свой метод
    public Specification<Product> build(ProductParamsDTO params) {
        return withCategoryId(params.getCategoryId())
                .and(priceGt(params.getPriceGt()))
                .and(priceLt(params.getPriceLt()))
                .and(ratingGt(params.getRatingGt()))
                .and(titleMatch(params.getTitleCont()));
    }

    private Specification<Product> withCategoryId(Long categoryId) {
        return (root, query, cb) -> categoryId == null ? cb.conjunction() : cb.equal(root.get("category").get("id"),categoryId);
    }


    private Specification<Product> priceGt(Integer priceGt) {
        return (root, query, cb) -> priceGt == null ? cb.conjunction() : cb.greaterThan(root.get("price"),priceGt);
    }

    private Specification<Product> priceLt(Integer priceLt) {
        return (root, query, cb) -> priceLt == null ? cb.conjunction() : cb.lessThan(root.get("price"),priceLt);
    }


    private Specification<Product> ratingGt(Double ratingGt) {
        return (root, query, cb) -> ratingGt == null ? cb.conjunction() : cb.greaterThan(root.get("rating"),ratingGt);
    }

    private Specification<Product> titleMatch(String titleCont) {
        return (root,query, cb) -> titleCont == null ? cb.conjunction() : cb.like(root.get("title"),titleCont);
    }

}
// END
