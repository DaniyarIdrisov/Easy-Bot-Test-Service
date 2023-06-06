package ru.daniyar.idrisov.testservice.models.jpa;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import ru.daniyar.idrisov.testservice.models.enums.ProductType;
import ru.daniyar.idrisov.testservice.models.jpa.base.AbstractEntity;
import ru.daniyar.idrisov.testservice.models.jpa.base.UpdatableEntity;

import java.util.HashMap;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product",
        uniqueConstraints =
                {@UniqueConstraint(name = "UniqueSerialNumberAndType", columnNames = {"serial_number", "type"})})
public class ProductEntity extends UpdatableEntity {

    @Column(name = "serial_number", nullable = false)
    private String serialNumber;

    private Double price;

    private Long count;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductType type;

    @ElementCollection
    @CollectionTable(name = "characteristics",
            joinColumns = {@JoinColumn(name = "product_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "head")
    @Column(name = "element")
    private Map<String, String> characteristics = new HashMap<>();

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producer_id")
    private ProducerEntity producer;

}
