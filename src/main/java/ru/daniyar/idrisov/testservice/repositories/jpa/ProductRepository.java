package ru.daniyar.idrisov.testservice.repositories.jpa;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.daniyar.idrisov.testservice.models.enums.ProductType;
import ru.daniyar.idrisov.testservice.models.jpa.ProductEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {

    Optional<ProductEntity> findBySerialNumberAndType(String serialNumber, ProductType type);

    List<ProductEntity> findAllByType(ProductType productType, Pageable pageable);

}
