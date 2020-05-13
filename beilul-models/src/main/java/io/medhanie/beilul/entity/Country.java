package io.medhanie.beilul.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity(name = "country")
public class Country {
    @Id
    @Column(columnDefinition = "char")
    private String countryIso3;
    @Column(columnDefinition = "char")
    private String countryIso2;
    private String countryName;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
    private List<AdminZone> adminZone;
}