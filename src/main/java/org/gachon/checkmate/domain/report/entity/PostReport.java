package org.gachon.checkmate.domain.report.entity;


import jakarta.persistence.*;
import lombok.*;
import org.gachon.checkmate.domain.member.entity.User;
import org.gachon.checkmate.domain.post.entity.Post;
import org.gachon.checkmate.global.common.BaseTimeEntity;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
@Entity
public class PostReport extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_report_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
    @Column(name = "reason")
    private String reason;

    public static PostReport createPostReport(User user, Post post, String reason) {
        return PostReport.builder()
                .user(user)
                .post(post)
                .reason(reason)
                .build();
    }
}
