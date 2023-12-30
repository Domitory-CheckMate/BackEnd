package org.gachon.checkmate.domain.post.entity;

import jakarta.persistence.*;
import lombok.*;
import org.gachon.checkmate.domain.checkList.entity.PostCheckList;
import org.gachon.checkmate.domain.post.converter.RoomTypeConverter;
import org.gachon.checkmate.domain.member.entity.RoomType;
import org.gachon.checkmate.domain.member.entity.User;
import org.gachon.checkmate.domain.post.converter.ImportantKeyTypeConverter;
import org.gachon.checkmate.domain.post.converter.SimilarityKeyTypeConverter;
import org.gachon.checkmate.domain.scrap.entity.Scrap;
import org.gachon.checkmate.global.common.BaseTimeEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
@Entity
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;
    private String title;
    private String content;
    private LocalDate endDate;
    @Convert(converter = RoomTypeConverter.class)
    private RoomType roomType;
    @Convert(converter = ImportantKeyTypeConverter.class)
    private ImportantKeyType importantKeyType;
    @Convert(converter = SimilarityKeyTypeConverter.class)
    private SimilarityKeyType similarityKeyType;
    @OneToOne(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private PostCheckList postCheckList;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "post")
    @Builder.Default
    private List<Scrap> scrapList = new ArrayList<>();
}
