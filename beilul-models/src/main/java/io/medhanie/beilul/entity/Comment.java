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
public class Comment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commentId;

    @NotNull
    @ManyToOne
    @JoinColumn(name="content_id")
    private Content content;
    @NotNull
    @Column(columnDefinition = "TEXT")
    private String comment;
    @NotNull
    @OneToMany
    @JoinColumn(name="reply_comment_id")
    private int replyCommentId;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "created_by")
    private Member createdBy;
    @NotNull
    @CreationTimestamp()
    private OffsetDateTime createdAt;
    @NotNull
    @CreationTimestamp
    private OffsetDateTime lastUpdate;
}
