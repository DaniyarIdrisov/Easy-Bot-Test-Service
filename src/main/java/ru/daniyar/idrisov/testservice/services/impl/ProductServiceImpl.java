package ru.daniyar.idrisov.testservice.services.impl;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.daniyar.idrisov.testservice.dto.request.ProductRequest;
import ru.daniyar.idrisov.testservice.dto.request.ProductUpdateRequest;
import ru.daniyar.idrisov.testservice.dto.response.ProductResponse;
import ru.daniyar.idrisov.testservice.exceptions.EntityConflictException;
import ru.daniyar.idrisov.testservice.exceptions.EntityNotFoundException;
import ru.daniyar.idrisov.testservice.exceptions.ServiceBadRequestException;
import ru.daniyar.idrisov.testservice.models.enums.ProductType;
import ru.daniyar.idrisov.testservice.models.jpa.ProducerEntity;
import ru.daniyar.idrisov.testservice.models.jpa.ProductEntity;
import ru.daniyar.idrisov.testservice.models.mappers.ProductMapper;
import ru.daniyar.idrisov.testservice.repositories.jpa.ProducerRepository;
import ru.daniyar.idrisov.testservice.repositories.jpa.ProductRepository;
import ru.daniyar.idrisov.testservice.services.ProductService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static ru.daniyar.idrisov.testservice.exceptions.constants.ExceptionConstants.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProducerRepository producerRepository;

    @Override
    @Transactional
    public ProductResponse addProduct(@NotNull ProductRequest productRequest) {
        var producerEntity = getProducer(productRequest);
        checkForEmptyFields(productRequest);
        checkForExisting(productRequest);
        var productEntity = productMapper.toProductEntity(productRequest);
        productEntity.setProducer(producerEntity);
        return productMapper.toProductResponse(productRepository.save(productEntity));
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(ProductUpdateRequest productUpdateRequest, UUID productId) {
        var productEntity = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException(PRODUCT_NOT_FOUNT_MESSAGE));
        if (productUpdateRequest.getSerialNumber() != null) {
            productEntity.setSerialNumber(productUpdateRequest.getSerialNumber());
        }
        if (productUpdateRequest.getType() != null) {
            productEntity.setType(productUpdateRequest.getType());
        }
        checkForExisting(productEntity);
        if (productUpdateRequest.getCount() != null) {
            productEntity.setCount(productUpdateRequest.getCount());
        }
        if (productUpdateRequest.getPrice() != null) {
            productEntity.setPrice(productUpdateRequest.getPrice());
        }
        if (productUpdateRequest.getCharacteristics() != null) {
            productEntity.getCharacteristics().clear();
            productEntity.setCharacteristics(productUpdateRequest.getCharacteristics());
        }
        updateProducerOfProduct(productEntity, productUpdateRequest);
        return productMapper.toProductResponse(productRepository.save(productEntity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable).stream()
                .map(productMapper::toProductResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProductById(UUID productId) {
        var productEntity = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException(PRODUCT_NOT_FOUNT_MESSAGE));
        return productMapper.toProductResponse(productEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getProductsByType(String type, Pageable pageable) {
        ProductType productType;
        try {
            productType = ProductType.valueOf(type);
        } catch (IllegalArgumentException e) {
            throw new ServiceBadRequestException(INCORRECT_PRODUCT_TYPE_MESSAGE, type);
        }
        return productRepository.findAllByType(productType, pageable).stream()
                .map(productMapper::toProductResponse)
                .collect(Collectors.toList());
    }

    private void updateProducerOfProduct(ProductEntity newProductEntity, ProductUpdateRequest productUpdateRequest) {
        if (productUpdateRequest.getProducerId() != null) {
            var producerEntity =  producerRepository.findById(productUpdateRequest.getProducerId())
                    .orElseThrow(() -> new EntityNotFoundException(PRODUCER_NOT_FOUNT_MESSAGE));
            newProductEntity.setProducer(producerEntity);
        }
        else {
            if (productUpdateRequest.isResetProducer()) {
                newProductEntity.setProducer(null);
            }
        }
    }

    private void checkForEmptyFields(ProductRequest productRequest) {
        if (productRequest.getSerialNumber().isEmpty()) {
            throw new ServiceBadRequestException(EMPTY_FIELD_MESSAGE, "serialNumber");
        }
        if (productRequest.getType() == null) {
            throw new ServiceBadRequestException(EMPTY_FIELD_MESSAGE, "type");
        }
    }

    private void checkForExisting(ProductRequest productRequest) {
        if (productRepository.findBySerialNumberAndType(
                productRequest.getSerialNumber(),
                productRequest.getType())
                .isPresent()) {
            throw new EntityConflictException(
                    PRODUCT_WITH_THIS_SERIAL_NUMBER_AND_TYPE_ALREADY_EXISTS_MESSAGE,
                    productRequest.getSerialNumber(),
                    productRequest.getType());
        }
    }

    private void checkForExisting(ProductEntity productEntity) {
        var checkProduct = productRepository.findBySerialNumberAndType(
                productEntity.getSerialNumber(),
                productEntity.getType());
        if (checkProduct.isPresent() && !checkProduct.get().getId().equals(productEntity.getId())) {
            throw new EntityConflictException(
                    PRODUCT_WITH_THIS_SERIAL_NUMBER_AND_TYPE_ALREADY_EXISTS_MESSAGE,
                    productEntity.getSerialNumber(),
                    productEntity.getType());
        }
    }

    private ProducerEntity getProducer(ProductRequest productRequest) {
        if (productRequest.getProducerId() == null) {
            return null;
        }
        return producerRepository.findById(productRequest.getProducerId())
                .orElseThrow(() -> new EntityNotFoundException(PRODUCER_NOT_FOUNT_MESSAGE));
    }

}
