package org.gachon.checkmate.domain.checkList.repository;

import org.gachon.checkmate.domain.checkList.entity.PostCheckList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCheckListRepository extends JpaRepository<PostCheckList, Long> {
}
