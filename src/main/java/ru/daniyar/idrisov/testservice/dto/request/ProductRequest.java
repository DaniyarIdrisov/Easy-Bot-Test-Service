package ru.daniyar.idrisov.testservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import ru.daniyar.idrisov.testservice.models.enums.ProductType;

import java.util.Map;
import java.util.UUID;

@Getter
public class ProductRequest {

    @NotBlank
    private String serialNumber;

    private Double price;

    private Long count;

    private ProductType type;

    private Map<String, String> characteristics;

    private UUID producerId;

}
