package io.medhanie.beilul.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;

@Entity
@Data
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private short roleId;

    @Column(unique = true)
    private String name;

    @NotNull
    @CreationTimestamp
    private OffsetDateTime createdAt;
    @NotNull
    @CreationTimestamp
    private OffsetDateTime lastUpdate;
}
