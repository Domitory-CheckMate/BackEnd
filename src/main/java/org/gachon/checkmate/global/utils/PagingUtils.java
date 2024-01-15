package org.gachon.checkmate.global.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.gachon.checkmate.global.error.exception.InvalidValueException;

import java.util.List;

import static org.gachon.checkmate.global.error.ErrorCode.INVALID_PAGING_SIZE;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PagingUtils {
    public static <T> List<T> convertPaging(List<T> dataList, long offset, int size) {
        if (dataList.size() <= offset)
            throw new InvalidValueException(INVALID_PAGING_SIZE);
        int startIndex = (int) offset;
        int endIndex = Math.min(dataList.size(), startIndex + size);
        return dataList.subList(startIndex, endIndex);
    }
}
