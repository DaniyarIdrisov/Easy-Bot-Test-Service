package ru.daniyar.idrisov.testservice.models.jpa;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import ru.daniyar.idrisov.testservice.models.jpa.base.AbstractEntity;
import ru.daniyar.idrisov.testservice.models.jpa.base.UpdatableEntity;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "producer")
public class ProducerEntity extends UpdatableEntity {

    private String name;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "producer", orphanRemoval = true)
    private List<ProductEntity> products = new ArrayList<>();

}
