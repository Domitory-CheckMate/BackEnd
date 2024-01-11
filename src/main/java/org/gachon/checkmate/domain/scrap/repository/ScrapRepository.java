package org.gachon.checkmate.domain.scrap.repository;

import org.gachon.checkmate.domain.scrap.entity.Scrap;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScrapRepository extends JpaRepository<Scrap, Long>, ScrapCustomRepository {
    void deleteByIdAndUserId(Long scrapId, Long UserId);
}
