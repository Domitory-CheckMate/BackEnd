package org.gachon.checkmate.domain.checkList.repository;

import org.gachon.checkmate.domain.checkList.entity.CheckList;
import org.gachon.checkmate.domain.member.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CheckListRepository extends JpaRepository<CheckList, Long> {
    Optional<CheckList> findByUserId(Long userId);

    boolean existsByUser(User user);
}
