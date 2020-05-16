package io.medhanie.beilul.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.OffsetDateTime;

@Entity
@Data
@NoArgsConstructor
public class AccessLog implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int accessLogId;
    @NotNull
    @ManyToOne
    @JoinColumn(name="content_id")
    private int contentId;
    @NotNull
    @ManyToOne
    @JoinColumn(name="member_id")
    private short memberId;
    @NotNull
    private String requestMethod;
    @NotNull
    private String responseStatus;
    @NotNull
    private String requestSize;
    @NotNull
    private String responseSize;
    @NotNull
    private short dbAccessTime;
    @NotNull
    private short apiAccessTime;
    @NotNull
    @CreationTimestamp
    private OffsetDateTime createdAt;

}
