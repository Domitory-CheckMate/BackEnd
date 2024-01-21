package org.gachon.checkmate.domain.post.entity;

import jakarta.persistence.*;
import lombok.*;
import org.gachon.checkmate.domain.checkList.entity.PostCheckList;
import org.gachon.checkmate.domain.member.entity.User;
import org.gachon.checkmate.domain.post.converter.*;
import org.gachon.checkmate.domain.post.dto.request.PostCreateRequestDto;
import org.gachon.checkmate.domain.post.dto.request.PostStateUpdateRequestDto;
import org.gachon.checkmate.domain.post.dto.request.PostUpdateRequestDto;
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
    @Convert(converter = PostStateConverter.class)
    private PostState postState;
    @Convert(converter = RoomTypeConverter.class)
    private RoomType roomType;
    @Convert(converter = DormitoryTypeConverter.class)
    private DormitoryType dormitoryType;
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

    public static Post createPost(PostCreateRequestDto postCreateRequestDto, User user) {
        Post post = Post.builder()
                .title(postCreateRequestDto.title())
                .content(postCreateRequestDto.content())
                .endDate(postCreateRequestDto.endDate())
                .postState(PostState.RECRUITING)
                .roomType(postCreateRequestDto.roomType())
                .dormitoryType(postCreateRequestDto.dormitoryType())
                .importantKeyType(postCreateRequestDto.importantKey())
                .similarityKeyType(postCreateRequestDto.similarityKey())
                .user(user)
                .build();
        user.addPost(post);
        return post;
    }

    public void setPostCheckList(PostCheckList postCheckList) {
        this.postCheckList = postCheckList;
    }

    public void addScrap(Scrap scrap) {
        this.scrapList.add(scrap);
    }

    public void updatePost(PostUpdateRequestDto postUpdateRequestDto) {
        this.title = postUpdateRequestDto.title();
        this.content = postUpdateRequestDto.content();
        this.importantKeyType = postUpdateRequestDto.importantKey();
        this.similarityKeyType = postUpdateRequestDto.similarityKey();
        this.roomType = postUpdateRequestDto.roomType();
        this.dormitoryType = postUpdateRequestDto.dormitoryType();
        this.endDate = postUpdateRequestDto.endDate();
        this.postCheckList.updatePostCheckList(postUpdateRequestDto.checkList());
    }

    public void updatePostState(PostStateUpdateRequestDto postStateUpdateRequestDto) {
        this.postState = postStateUpdateRequestDto.postState();
    }
}
