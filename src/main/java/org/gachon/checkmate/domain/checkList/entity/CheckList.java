package org.gachon.checkmate.domain.checkList.entity;

import jakarta.persistence.*;
import lombok.*;
import org.gachon.checkmate.domain.checkList.converter.*;
import org.gachon.checkmate.domain.checkList.dto.request.CheckListRequestDto;
import org.gachon.checkmate.domain.member.entity.User;
import org.gachon.checkmate.global.common.BaseTimeEntity;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
@Entity
public class CheckList extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "check_list_id")
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
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public static CheckList createCheckList(User user, CheckListRequestDto checkListRequestDto){
        CheckList checkList = CheckList.builder()
                .cleanType(checkListRequestDto.cleanType())
                .drinkType(checkListRequestDto.drinkType())
                .homeType(checkListRequestDto.homeType())
                .lifePatternType(checkListRequestDto.lifePatternType())
                .noiseType(checkListRequestDto.noiseType())
                .sleepType(checkListRequestDto.sleepType())
                .user(user)
                .build();
        user.setCheckList(checkList);
        return checkList;
    }

    public void updateCheckList(CheckListRequestDto checkListRequestDto) {
        this.cleanType = checkListRequestDto.cleanType();
        this.drinkType = checkListRequestDto.drinkType();
        this.homeType = checkListRequestDto.homeType();
        this.lifePatternType = checkListRequestDto.lifePatternType();
        this.noiseType = checkListRequestDto.noiseType();
        this.sleepType = checkListRequestDto.sleepType();
    }
}
