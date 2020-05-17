package io.medhanie.beilul.entity;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@NoArgsConstructor
@Data
public class AdminZone implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private short adminZoneId;

    private String adminZoneName;

    @ManyToOne
    @JoinColumn(name = "country_id", columnDefinition = "char")
    private Country country;

    @OneToMany(mappedBy = "adminZoneId", cascade = CascadeType.ALL)
    private List<City> city;

    @CreationTimestamp
    private OffsetDateTime createdAt;

    @CreationTimestamp
    private OffsetDateTime lastUpdate;
}
