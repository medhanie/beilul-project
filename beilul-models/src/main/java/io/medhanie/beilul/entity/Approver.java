package io.medhanie.beilul.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.OffsetDateTime;

public class Approver implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int approverId;
    @NotNull
    @ManyToOne
    @JoinColumn(name="content_id")
    private Content content;
    @NotNull
    private boolean isApproved;
    @NotNull
    private String reason;
    @NotNull
    @CreationTimestamp()
    private OffsetDateTime createdAt;
    @NotNull
    @CreationTimestamp
    private OffsetDateTime lastUpdate;
}
