package org.career.my5g.domain.repository;

import java.util.Optional;

import org.career.my5g.domain.entity.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserDetail, Long> {
    Optional<UserDetail> findByAccount(String account);
}
