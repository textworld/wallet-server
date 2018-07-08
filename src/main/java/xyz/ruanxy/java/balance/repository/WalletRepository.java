package xyz.ruanxy.java.balance.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.ruanxy.java.balance.model.Wallet;
import xyz.ruanxy.java.balance.model.WalletType;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Page<Wallet> findByCreatedAt(Long userId, Pageable pageable);
    Page<Wallet> findByUserId(Long userId, Pageable pageable);
    Page<Wallet> findByType(WalletType type, Pageable pageable);
}
