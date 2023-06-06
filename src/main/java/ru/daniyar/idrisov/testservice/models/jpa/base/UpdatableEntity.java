package ru.daniyar.idrisov.testservice.models.jpa.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class UpdatableEntity extends AbstractEntity {

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Instant updatedAt;

}
