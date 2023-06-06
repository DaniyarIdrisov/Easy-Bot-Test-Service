package ru.daniyar.idrisov.testservice.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.daniyar.idrisov.testservice.models.jpa.ProducerEntity;

import java.util.UUID;

@Repository
public interface ProducerRepository extends JpaRepository<ProducerEntity, UUID> {
}
