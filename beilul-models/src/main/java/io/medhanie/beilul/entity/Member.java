package io.medhanie.beilul.entity;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.OffsetDateTime;

@Entity(name = "membership")
@Data
@NoArgsConstructor
public class Member implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int memberId;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;
    private String picture;
    private String pictureThumb;
    @NotNull
    private String email;
    @Column(name = "active")
    private boolean isActive;
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    @CreationTimestamp()
    private OffsetDateTime createdAt;
    @NotNull
    @CreationTimestamp
    private OffsetDateTime lastUpdate;

}
