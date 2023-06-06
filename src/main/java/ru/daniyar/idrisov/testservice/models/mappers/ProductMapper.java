package ru.daniyar.idrisov.testservice.models.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;
import ru.daniyar.idrisov.testservice.dto.request.ProductRequest;
import ru.daniyar.idrisov.testservice.dto.request.ProductUpdateRequest;
import ru.daniyar.idrisov.testservice.dto.response.ProductResponse;
import ru.daniyar.idrisov.testservice.models.jpa.ProductEntity;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {ProducerMapper.class})
public interface ProductMapper {

    ProductResponse toProductResponse(ProductEntity productEntity);

    ProductEntity toProductEntity(ProductRequest productRequest);

    ProductEntity toProductEntity(ProductUpdateRequest productUpdateRequest);

}
