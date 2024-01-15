package org.gachon.checkmate.domain.post.utils;

import org.gachon.checkmate.domain.post.dto.response.PostSearchElementResponseDto;

import java.util.Comparator;
import java.util.List;

public class PostSortingUtils {
    public static void sortByTypeForSearchResults(List<PostSearchElementResponseDto> posts, PostSortType postSortType) {
        if (PostSortType.ACCURACY.equals(postSortType))
            sortByAccuracyType(posts);
        else if (PostSortType.REMAIN_DATE.equals(postSortType))
            sortByRemainDate(posts);
    }

    private static void sortByAccuracyType(List<PostSearchElementResponseDto> posts) {
        posts.sort(Comparator.comparingInt(PostSearchElementResponseDto::accuracy));
    }

    private static void sortByRemainDate(List<PostSearchElementResponseDto> posts) {
        posts.sort(Comparator.comparingInt(PostSearchElementResponseDto::remainDate));
    }
}
