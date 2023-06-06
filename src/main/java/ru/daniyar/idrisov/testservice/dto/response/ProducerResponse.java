package ru.daniyar.idrisov.testservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProducerResponse {

    private UUID id;

    private Instant createdAt;

    private Instant updatedAt;

    private String name;

}
