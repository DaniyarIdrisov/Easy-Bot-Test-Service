package ru.daniyar.idrisov.testservice.dto.response;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.daniyar.idrisov.testservice.models.enums.ProductType;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {

    private UUID id;

    private Instant createdAt;

    private Instant updatedAt;

    private String serialNumber;

    private Double price;

    private Long count;

    private ProductType type;

    private Map<String, String> characteristics;

    private ProducerResponse producer;

}
