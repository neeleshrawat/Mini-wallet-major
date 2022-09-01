package com.gfg.majorProject.TransactionService.repos;

import com.gfg.majorProject.TransactionService.entites.Transaction;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TransactionRepository extends CrudRepository<Transaction,Long> {
    Optional<Transaction> findByTxId(String txId);
}
