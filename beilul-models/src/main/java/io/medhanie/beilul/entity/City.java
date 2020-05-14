package io.medhanie.beilul.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class City implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private short cityId;
    private String cityName;
    @ManyToOne
    @JoinColumn(name = "admin_zone_id")
    private AdminZone adminZoneId;
    private BigDecimal latitude;
    private BigDecimal longitude;
}
