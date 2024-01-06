package org.gachon.checkmate.domain.chat.dto.request;

public record ChatListRequestDto(
        Long otherUserId,
        Integer pageNumber,
        Integer pageSize
) {
    public ChatListRequestDto(Long otherUserId, Integer pageNumber, Integer pageSize) {
        this.otherUserId = otherUserId;
        this.pageNumber = (pageNumber != null) ? pageNumber : 0;
        this.pageSize = pageSize != null ? pageSize : 20;
    }
}
