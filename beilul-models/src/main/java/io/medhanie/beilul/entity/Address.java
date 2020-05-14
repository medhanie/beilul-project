package io.medhanie.beilul.entity;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.OffsetDateTime;

@Entity
@Data
@NoArgsConstructor
public class Address implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int addressId;
    @NotNull
    private String address;
    private String address2;
    private String district;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;
    private String cityName;
    private String postalCode;

    private String phone;
    @NotNull
    @CreationTimestamp()
    private OffsetDateTime createdAt;
    @NotNull
    @CreationTimestamp
    private OffsetDateTime lastUpdate;
}
