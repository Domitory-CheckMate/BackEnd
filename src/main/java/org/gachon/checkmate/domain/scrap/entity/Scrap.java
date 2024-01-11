package org.gachon.checkmate.domain.scrap.entity;

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
public class Scrap extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "scrap_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public static Scrap createScrap(User user, Post post) {
        Scrap scrap = Scrap.builder()
                .user(user)
                .post(post)
                .build();
        user.addScrap(scrap);
        post.addScrap(scrap);
        return scrap;
    }
}
