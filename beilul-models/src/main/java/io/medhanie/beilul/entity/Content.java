package io.medhanie.beilul.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Content implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int contentId;

    @NotNull
    private String title;

    @NotNull
    @Column(columnDefinition = "MEDIUMTEXT")
    private String body;

    @NotNull
    private String Summary;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "created_by")
    private Member createdBy;

    @OneToMany(mappedBy = "content", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(mappedBy = "content", cascade = CascadeType.ALL)
    private List<Approver> approvers;

    @ManyToMany
    @JoinTable(
            name = "content_category",
            joinColumns = {@JoinColumn(name = "content_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")}
    )
    private List<Category> categories;

    @ManyToMany
    @JoinTable(
            name = "language_content",
            joinColumns = {@JoinColumn(name = "content_id")},
            inverseJoinColumns = {@JoinColumn(name = "language_id")}
    )
    private List<Language> languages;

    @CreationTimestamp
    private OffsetDateTime createdAt;

    @CreationTimestamp
    private OffsetDateTime lastUpdate;
}
