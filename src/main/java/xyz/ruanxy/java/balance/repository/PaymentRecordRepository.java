package xyz.ruanxy.java.balance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.ruanxy.java.balance.model.PaymentRecord;

@Repository
public interface PaymentRecordRepository extends JpaRepository<PaymentRecord, Long> {
    boolean existsByOrderId(String orderId);
    PaymentRecord findByOrderId(String orderId);
}
