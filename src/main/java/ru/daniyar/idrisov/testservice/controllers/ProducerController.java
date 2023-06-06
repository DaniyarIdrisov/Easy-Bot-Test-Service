package ru.daniyar.idrisov.testservice.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.daniyar.idrisov.testservice.dto.request.ProducerRequest;
import ru.daniyar.idrisov.testservice.dto.response.ProducerResponse;
import ru.daniyar.idrisov.testservice.services.ProducerService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/producer")
public class ProducerController {

    private final ProducerService producerService;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPER_ADMIN')")
    @PostMapping
    public ProducerResponse addProducer(@RequestBody @Valid ProducerRequest producerRequest) {
        return producerService.addProducer(producerRequest);
    }

}
