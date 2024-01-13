package org.gachon.checkmate.domain.report.repository;

import org.gachon.checkmate.domain.report.entity.PostReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostReportRepository extends JpaRepository<PostReport, Long> {

    Boolean existsPostReportByUserIdAndPostId(Long userId, Long postId);
}
