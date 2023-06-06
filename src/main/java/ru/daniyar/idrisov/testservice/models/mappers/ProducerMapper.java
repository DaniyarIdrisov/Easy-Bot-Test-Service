package ru.daniyar.idrisov.testservice.models.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;
import ru.daniyar.idrisov.testservice.dto.request.ProducerRequest;
import ru.daniyar.idrisov.testservice.dto.response.ProducerResponse;
import ru.daniyar.idrisov.testservice.models.jpa.ProducerEntity;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProducerMapper {

    ProducerResponse toProducerResponse(ProducerEntity producerEntity);

    ProducerEntity toProducerEntity(ProducerRequest producerRequest);

}
