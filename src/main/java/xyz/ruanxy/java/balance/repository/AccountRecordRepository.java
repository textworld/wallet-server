package xyz.ruanxy.java.balance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.ruanxy.java.balance.model.AccountRecordModel;

@Repository
public interface AccountRecordRepository extends JpaRepository<AccountRecordModel, Long> {

}
