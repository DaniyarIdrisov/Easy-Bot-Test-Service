package ru.daniyar.idrisov.testservice.services;

import org.springframework.data.domain.Pageable;
import ru.daniyar.idrisov.testservice.dto.request.ProductRequest;
import ru.daniyar.idrisov.testservice.dto.request.ProductUpdateRequest;
import ru.daniyar.idrisov.testservice.dto.response.ProductResponse;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    ProductResponse addProduct(ProductRequest productRequest);

    ProductResponse updateProduct(ProductUpdateRequest productUpdateRequest, UUID productId);

    List<ProductResponse> getAllProducts(Pageable pageable);

    ProductResponse getProductById(UUID productId);

    List<ProductResponse> getProductsByType(String type, Pageable pageable);
}
