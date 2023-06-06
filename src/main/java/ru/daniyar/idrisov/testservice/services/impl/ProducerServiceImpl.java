package ru.daniyar.idrisov.testservice.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.daniyar.idrisov.testservice.dto.request.ProducerRequest;
import ru.daniyar.idrisov.testservice.dto.response.ProducerResponse;
import ru.daniyar.idrisov.testservice.exceptions.ServiceBadRequestException;
import ru.daniyar.idrisov.testservice.models.mappers.ProducerMapper;
import ru.daniyar.idrisov.testservice.repositories.jpa.ProducerRepository;
import ru.daniyar.idrisov.testservice.services.ProducerService;

import static ru.daniyar.idrisov.testservice.exceptions.constants.ExceptionConstants.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProducerServiceImpl implements ProducerService {

    private final ProducerRepository producerRepository;
    private final ProducerMapper producerMapper;


    @Override
    public ProducerResponse addProducer(ProducerRequest producerRequest) {
        checkForEmptyFields(producerRequest);
        var producerEntity = producerMapper.toProducerEntity(producerRequest);
        return producerMapper.toProducerResponse(producerRepository.save(producerEntity));
    }

    private void checkForEmptyFields(ProducerRequest producerRequest) {
        if (producerRequest.getName().isEmpty()) {
            throw new ServiceBadRequestException(EMPTY_FIELD_MESSAGE, "name");
        }
    }

}
