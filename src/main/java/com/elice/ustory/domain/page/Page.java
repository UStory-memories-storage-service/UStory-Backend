package com.elice.ustory.domain.page;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.ustory.ustory_service.domain.comment.entity.Comment;

// TODO: 엔티티 ERD 참조하여 작성
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "page")
public class Page {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String title;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Long writer_id;

    @ManyToOne
    @JoinColumn(name = "diary_id")
    private Long diary_id;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Long address_id;

    @OneToMany(mappedBy = "page")
    private List<Comment> comments;

    @Column(nullable = false)
    private LocalDate target_date;

    @Column(nullable = false, length = 8)
    private String delete;

    @Column(nullable = false, length = 10)
    private String locked;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    LocalDateTime created_at;

    @LastModifiedDate
    @Column(nullable = false)
    LocalDateTime modified_at;

}
