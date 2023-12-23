package org.gachon.checkmate.domain.post.entity;

import jakarta.persistence.*;
import lombok.*;
import org.gachon.checkmate.domain.checkList.entity.PostCheckList;
import org.gachon.checkmate.domain.post.converter.ImportantKeyTypeConverter;
import org.gachon.checkmate.domain.post.converter.SimilarityKeyTypeConverter;
import org.gachon.checkmate.domain.scrap.entity.Scrap;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;
    private String title;
    private String content;
    private LocalDate EndDate;
    @Convert(converter = ImportantKeyTypeConverter.class)
    private ImportantKeyType importantKeyType;
    @Convert(converter = SimilarityKeyTypeConverter.class)
    private SimilarityKeyType similarityKeyType;
    @OneToOne(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private PostCheckList postCheckList;
    @OneToMany(mappedBy = "post")
    @Builder.Default
    private List<PostMaker> postMakerList = new ArrayList<>();
    @OneToMany(mappedBy = "post")
    @Builder.Default
    private List<Scrap> scrapList = new ArrayList<>();
}
