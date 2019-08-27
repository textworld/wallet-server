package xyz.ruanxy.java.balance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.ruanxy.java.balance.model.AccountModel;

@Repository
public interface WalletRepository extends JpaRepository<AccountModel, Long> {

}
