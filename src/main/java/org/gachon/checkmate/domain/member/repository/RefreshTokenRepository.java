package org.gachon.checkmate.domain.member.repository;

import org.gachon.checkmate.domain.member.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {
}
