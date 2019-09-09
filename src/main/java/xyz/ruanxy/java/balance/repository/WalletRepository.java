package xyz.ruanxy.java.balance.repository;

import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import xyz.ruanxy.java.balance.model.AccountModel;

@Repository
public interface WalletRepository extends JpaRepository<AccountModel, Long> {
    List<AccountModel> findByActiveOrderByIdDesc(int active);

    //@Query(value = "select * from T_account as a where a.active = 1 and a.id not in (select r.account_id from T_account_record as r where r.transaction_id = :transactionId)", nativeQuery = true)
    @Query(value = "select * from t_account as a where a.active = 1 and a.id not in (select r.account_id from t_account_record as r where r.transaction_id = :id)", nativeQuery = true)
    List<Map<String, Object>> findUnRecordAccount(@Param("id") long id);
}
