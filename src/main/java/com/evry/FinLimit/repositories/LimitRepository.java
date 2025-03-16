package com.evry.FinLimit.repositories;

import com.evry.FinLimit.entity.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 */
@Repository
public interface LimitRepository extends JpaRepository<Limit, Long> {
    Boolean existsByAccountFrom(Long accountId);

    @Query(value = "SELECT * FROM limits WHERE account_from = :accountId ORDER BY limit_datetime ASC", nativeQuery = true)
    List<Limit> findByAccountIdOrderByDateAsc(@Param("accountId") Long accountId);
}
