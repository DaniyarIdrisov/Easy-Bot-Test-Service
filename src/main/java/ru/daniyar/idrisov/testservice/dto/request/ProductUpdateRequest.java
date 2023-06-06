package ru.daniyar.idrisov.testservice.dto.request;

import lombok.Getter;
import ru.daniyar.idrisov.testservice.models.enums.ProductType;

import java.util.Map;
import java.util.UUID;

@Getter
public class ProductUpdateRequest {

    private String serialNumber;

    private Double price;

    private Long count;

    private ProductType type;

    private Map<String, String> characteristics;

    private UUID producerId;

    private boolean resetProducer;

}
