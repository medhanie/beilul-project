package io.medhanie.beilul.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class AdminZone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private short adminZoneId;
    private String adminZoneName;

    @ManyToOne
    @JoinColumn(name = "country_id", columnDefinition = "char")
    private Country country;

    @OneToMany(mappedBy = "adminZoneId", cascade = CascadeType.ALL)
    private List<City> city;
}
