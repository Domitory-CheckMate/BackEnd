package org.gachon.checkmate.domain.checkList.utils;

import org.gachon.checkmate.domain.checkList.entity.CheckList;
import org.gachon.checkmate.domain.checkList.entity.PostCheckList;

public class MatchRateCalculator {
    public static int getAccuracy(PostCheckList postCheckList, CheckList checkList) {
        int count = 0;
        count += postCheckList.getCleanType().compareRateTo(checkList.getCleanType());
        count += postCheckList.getDrinkType().compareRateTo(checkList.getDrinkType());
        count += postCheckList.getHomeType().compareRateTo(checkList.getHomeType());
        count += postCheckList.getLifePatternType().compareRateTo(checkList.getLifePatternType());
        count += postCheckList.getNoiseType().compareRateTo(checkList.getNoiseType());
        count += postCheckList.getSleepGridingType().compareRateTo(checkList.getSleepGridingType());
        count += postCheckList.getSleepSnoreType().compareRateTo(checkList.getSleepSnoreType());
        count += postCheckList.getSleepTalkingType().compareRateTo(checkList.getSleepTalkingType());
        count += postCheckList.getSleepTurningType().compareRateTo(checkList.getSleepTurningType());
        count += postCheckList.getSmokeType().compareRateTo(checkList.getSmokeType());
        return (int) (count / 10) * 100;
    }

}