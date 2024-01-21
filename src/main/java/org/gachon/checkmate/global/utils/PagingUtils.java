package org.gachon.checkmate.global.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.gachon.checkmate.global.error.exception.EntityNotFoundException;
import org.gachon.checkmate.global.error.exception.InvalidValueException;

import java.util.List;

import static org.gachon.checkmate.global.error.ErrorCode.INVALID_PAGING_SIZE;
import static org.gachon.checkmate.global.error.ErrorCode.POST_LIST_NOT_FOUND;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PagingUtils {
    public static <T> List<T> convertPaging(List<T> dataList, long offset, int size) {
        validFilterResult(dataList);
        validOffsetSize(dataList, offset);
        int startIndex = (int) offset;
        int endIndex = Math.min(dataList.size(), startIndex + size);
        return dataList.subList(startIndex, endIndex);
    }

    private static <T> void validFilterResult(List<T> dataList) {
        if (dataList.size() == 0)
            throw new EntityNotFoundException(POST_LIST_NOT_FOUND);
    }

    private static <T> void validOffsetSize(List<T> dataList, long offset){
        if (dataList.size() <= offset)
            throw new InvalidValueException(INVALID_PAGING_SIZE);
    }
}
