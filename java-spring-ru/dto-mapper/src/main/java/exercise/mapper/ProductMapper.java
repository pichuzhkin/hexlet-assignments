package exercise.mapper;

import exercise.dto.ProductCreateDTO;
import exercise.dto.ProductDTO;
import exercise.dto.ProductUpdateDTO;
import exercise.model.Product;
import org.mapstruct.*;

// BEGIN
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;


@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class ProductMapper {
    @Mapping(source = "vendorCode", target = "barcode")
    @Mapping(source = "title", target = "name")
    @Mapping(source = "price", target = "cost")
    public abstract Product map(ProductCreateDTO dto);

    @Mapping(source = "price", target = "cost")
    public abstract void update(ProductUpdateDTO dto, @MappingTarget Product model);


    @Mapping(target = "vendorCode", source = "barcode")
    @Mapping(target = "title", source = "name")
    @Mapping(target = "price", source = "cost")
    public abstract ProductDTO map(Product model);
}
// END
