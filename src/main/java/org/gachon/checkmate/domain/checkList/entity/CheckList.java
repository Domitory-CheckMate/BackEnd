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
    @Convert(converter = CallTypeConverter.class)
    private CallType callType;
    @Convert(converter = EarPhoneTypeConverter.class)
    private EarPhoneType earPhoneType;
    @Convert(converter = SleepGridingTypeConverter.class)
    private SleepGridingType sleepGridingType;
    @Convert(converter = SleepSnoreTypeConverter.class)
    private SleepSnoreType sleepSnoreType;
    @Convert(converter = SleepTalkingTypeConverter.class)
    private SleepTalkingType sleepTalkingType;
    @Convert(converter = SleepTurningTypeConverter.class)
    private SleepTurningType sleepTurningType;
    @Convert(converter = SmokeTypeConverter.class)
    private SmokeType smokeType;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public static CheckList createCheckList(User user, CheckListRequestDto checkListRequestDto) {
        CheckList checkList = CheckList.builder()
                .cleanType(checkListRequestDto.cleanType())
                .drinkType(checkListRequestDto.drinkType())
                .homeType(checkListRequestDto.homeType())
                .lifePatternType(checkListRequestDto.lifePatternType())
                .callType(checkListRequestDto.callType())
                .earPhoneType(checkListRequestDto.earPhoneType())
                .sleepGridingType(checkListRequestDto.sleepGridingType())
                .sleepSnoreType(checkListRequestDto.sleepSnoreType())
                .sleepTalkingType(checkListRequestDto.sleepTalkingType())
                .sleepTurningType(checkListRequestDto.sleepTurningType())
                .smokeType(checkListRequestDto.smokeType())
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
        this.callType = checkListRequestDto.callType();
        this.earPhoneType = checkListRequestDto.earPhoneType();
        this.sleepGridingType = checkListRequestDto.sleepGridingType();
        this.sleepSnoreType = checkListRequestDto.sleepSnoreType();
        this.sleepTalkingType = checkListRequestDto.sleepTalkingType();
        this.sleepTurningType = checkListRequestDto.sleepTurningType();
        this.smokeType = checkListRequestDto.smokeType();
    }
}
