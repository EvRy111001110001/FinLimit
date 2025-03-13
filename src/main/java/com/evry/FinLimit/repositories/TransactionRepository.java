package com.evry.FinLimit.repositories;

import com.evry.FinLimit.entity.Transaction;
import com.evry.FinLimit.model.TransactionWithLimitDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**

 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query(value = """
        SELECT t.*, 
               l.limit_datetime, 
               l.limit_sum AS limit_amount, 
               l.limit_currency_shortname AS limit_currency 
        FROM transactions t
        JOIN limits l ON l.account_from = t.account_from  
        AND l.limit_datetime = (
            SELECT MAX(l2.limit_datetime) 
            FROM limits l2 
            WHERE l2.account_from = t.account_from
            AND l2.limit_datetime <= t.datetime
        )
        WHERE t.account_from = :accountId
        ORDER BY t.datetime, t.expense_category
        """, nativeQuery = true)
    List<TransactionWithLimitDTO> findTransactionsWithLimit(@Param("accountId") Long accountId);
}
