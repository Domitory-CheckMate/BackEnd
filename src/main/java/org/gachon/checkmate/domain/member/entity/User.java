package org.gachon.checkmate.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;
import org.gachon.checkmate.domain.checkList.entity.CheckList;
import org.gachon.checkmate.domain.member.converter.GenderTypeConverter;
import org.gachon.checkmate.domain.member.converter.MbtiTypeConverter;
import org.gachon.checkmate.domain.post.converter.RoomTypeConverter;
import org.gachon.checkmate.domain.post.entity.Post;
import org.gachon.checkmate.domain.scrap.entity.Scrap;
import org.gachon.checkmate.global.common.BaseTimeEntity;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
@Table(name = "users")
@Entity
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    private String email;
    private String password;
    private String name;
    private String profile;
    private String school;
    private String major;
    @Convert(converter = MbtiTypeConverter.class)
    private MbtiType mbtiType;
    @Convert(converter = GenderTypeConverter.class)
    private GenderType gender;
    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private CheckList checkList;
    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<Post> postList = new ArrayList<>();
    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<Scrap> scrapList = new ArrayList<>();
}
