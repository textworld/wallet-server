package xyz.ruanxy.java.balance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRecordRepository extends JpaRepository<AccountRecordRepository, Long> {

}
