package io.medhanie.beilul.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.OffsetDateTime;

@Entity
@Data
@NoArgsConstructor
public class Approver implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int approveId;

    @NotNull
    @ManyToOne
    @JoinColumn(name="content_id")
    private Content content;

    @NotNull
    @ManyToOne
    @JoinColumn(name="approved_by")
    private Member member;

    @NotNull
    private boolean isApproved;

    @NotNull
    private String reason;

    @CreationTimestamp
    private OffsetDateTime createdAt;

    @CreationTimestamp
    private OffsetDateTime lastUpdate;
}
