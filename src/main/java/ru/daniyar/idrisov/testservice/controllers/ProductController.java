package ru.daniyar.idrisov.testservice.controllers;

import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.daniyar.idrisov.testservice.dto.request.ProductRequest;
import ru.daniyar.idrisov.testservice.dto.request.ProductUpdateRequest;
import ru.daniyar.idrisov.testservice.dto.response.ProductResponse;
import ru.daniyar.idrisov.testservice.models.enums.ProductType;
import ru.daniyar.idrisov.testservice.services.ProductService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService productService;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPER_ADMIN')")
    @PostMapping
    public ProductResponse addProduct(@RequestBody @Valid ProductRequest productRequest) {
        return productService.addProduct(productRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPER_ADMIN')")
    @PutMapping("/{id}")
    public ProductResponse updateProduct(@RequestBody ProductUpdateRequest productUpdateRequest,
                                         @PathVariable("id") UUID productId) {
        return productService.updateProduct(productUpdateRequest, productId);
    }

    @PermitAll
    @GetMapping
    public List<ProductResponse> getAllProducts(Pageable pageable) {
        return productService.getAllProducts(pageable);
    }

    @PermitAll
    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable("id") UUID productId) {
        return productService.getProductById(productId);
    }

    @PermitAll
    @GetMapping("/type")
    public List<ProductResponse> getProductsByType(@RequestParam("type") String type, Pageable pageable) {
        return productService.getProductsByType(type, pageable);
    }

}
