package org.gachon.checkmate.domain.checkList.entity;

import jakarta.persistence.*;
import lombok.*;
import org.gachon.checkmate.domain.checkList.converter.*;
import org.gachon.checkmate.domain.checkList.dto.request.CheckListRequestDto;
import org.gachon.checkmate.domain.post.entity.Post;
import org.gachon.checkmate.global.common.BaseTimeEntity;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
@Entity
public class PostCheckList extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_check_list_id")
    private Long id;
    @Convert(converter = CleanTypeConverter.class)
    private CleanType cleanType;
    @Convert(converter = DrinkTypeConverter.class)
    private DrinkType drinkType;
    @Convert(converter = HomeTypeConverter.class)
    private HomeType homeType;
    @Convert(converter = LifePatternTypeConverter.class)
    private LifePatternType lifePatternType;
    @Convert(converter = NoiseTypeConverter.class)
    private NoiseType noiseType;
    @Convert(converter = SleepTypeConverter.class)
    private SleepType sleepType;
    @Convert(converter = SmokeTypeConverter.class)
    private SmokeType smokeType;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public static PostCheckList createPostCheckList(CheckListRequestDto checkListRequestDto, Post post) {
        PostCheckList checkList = PostCheckList.builder()
                .cleanType(checkListRequestDto.cleanType())
                .drinkType(checkListRequestDto.drinkType())
                .homeType(checkListRequestDto.homeType())
                .lifePatternType(checkListRequestDto.lifePatternType())
                .noiseType(checkListRequestDto.noiseType())
                .sleepType(checkListRequestDto.sleepType())
                .smokeType(checkListRequestDto.smokeType())
                .post(post)
                .build();
        post.setPostCheckList(checkList);
        return checkList;
    }

    public void updatePostCheckList(CheckListRequestDto checkListRequestDto) {
        this.cleanType = checkListRequestDto.cleanType();
        this.drinkType = checkListRequestDto.drinkType();
        this.homeType = checkListRequestDto.homeType();
        this.lifePatternType = checkListRequestDto.lifePatternType();
        this.noiseType = checkListRequestDto.noiseType();
        this.sleepType = checkListRequestDto.sleepType();
        this.smokeType = checkListRequestDto.smokeType();
    }
}
