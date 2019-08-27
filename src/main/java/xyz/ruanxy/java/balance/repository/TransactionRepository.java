package xyz.ruanxy.java.balance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import xyz.ruanxy.java.balance.model.TransactionModel;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionModel, Long> {
}
