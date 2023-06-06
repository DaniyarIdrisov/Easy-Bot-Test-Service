package ru.daniyar.idrisov.testservice.services;

import ru.daniyar.idrisov.testservice.dto.request.ProducerRequest;
import ru.daniyar.idrisov.testservice.dto.response.ProducerResponse;

public interface ProducerService {

    ProducerResponse addProducer(ProducerRequest producerRequest);

}
