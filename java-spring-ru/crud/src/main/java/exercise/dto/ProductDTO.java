package exercise.dto;

import exercise.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ProductDTO implements BaseEntity {
    private Long id;
    private Long categoryId;
    private  String categoryName;
    private String title;
    private int price;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
