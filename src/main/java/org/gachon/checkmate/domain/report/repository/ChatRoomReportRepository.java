package org.gachon.checkmate.domain.report.repository;

import org.gachon.checkmate.domain.report.entity.ChatRoomReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomReportRepository extends JpaRepository<ChatRoomReport, Long> {

    Boolean existsChatRoomReportByUserIdAndChatRoomId(Long userId, String chatRoomId);
}
