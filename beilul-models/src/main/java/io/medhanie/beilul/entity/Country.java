package io.medhanie.beilul.entity;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Data
@NoArgsConstructor
@Entity(name = "country")
public class Country implements Serializable {
    @Id
    @Column(columnDefinition = "char")
    private String countryIso3;

    @Column(columnDefinition = "char")
    private String countryIso2;

    private String countryName;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
    private List<AdminZone> adminZone;

    @CreationTimestamp
    private OffsetDateTime createdAt;

    @CreationTimestamp
    private OffsetDateTime lastUpdate;
}