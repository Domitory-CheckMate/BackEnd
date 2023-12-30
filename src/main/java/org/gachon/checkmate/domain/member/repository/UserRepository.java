package org.gachon.checkmate.domain.member.repository;

import org.gachon.checkmate.domain.member.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
