package org.gachon.checkmate.domain.post.utils;

import org.gachon.checkmate.domain.post.dto.response.PostSearchElementResponseDto;

import java.util.Comparator;
import java.util.List;

public class PostSortingUtils {
    public static void sortByTypeForSearchResults(List<PostSearchElementResponseDto> posts, PostSortType postSortType) {
        if (PostSortType.ACCURACY.equals(postSortType))
            sortByAccuracy(posts);
        else if (PostSortType.REMAIN_DATE.equals(postSortType))
            sortByRemainDate(posts);
        else if (PostSortType.SCRAP.equals(postSortType))
            sortByScrapCount(posts);

    }

    private static void sortByAccuracy(List<PostSearchElementResponseDto> posts) {
        posts.sort(Comparator.comparingInt(PostSearchElementResponseDto::accuracy).reversed()
                .thenComparing(dto -> filterRegisterDate(dto.remainDate())));
    }

    private static void sortByRemainDate(List<PostSearchElementResponseDto> posts) {
        posts.sort(Comparator.comparingInt(dto -> filterPositiveRemainDate(dto.remainDate())));
    }

    private static void sortByScrapCount(List<PostSearchElementResponseDto> posts) {
        posts.sort(Comparator.comparingInt(PostSearchElementResponseDto::scrapCount).reversed()
                .thenComparing(dto -> filterRegisterDate(dto.remainDate())));
    }

    private static int filterPositiveRemainDate(int remainDate) {
        return remainDate < 0 ? Integer.MAX_VALUE : remainDate;
    }

    private static int filterRegisterDate(int remainDate) {
        return remainDate < 0 ? Integer.MAX_VALUE : 0;
    }
}
