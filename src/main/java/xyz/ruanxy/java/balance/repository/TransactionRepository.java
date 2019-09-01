package xyz.ruanxy.java.balance.repository;

import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import xyz.ruanxy.java.balance.model.TransactionModel;
import xyz.ruanxy.java.balance.payload.vo.AccountRecordVO;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionModel, Long> {
    /*@Query(value = "select new xyz.ruanxy.java.balance.payload.vo.AccountRecordVO(r.id, a.id, r.transaction_id, r.money, a.name, a.comment, r.gmt_transaction)"
        + " from AccountRecordModel r left join r.account a")
    */
    /*@Query(value = "select new xyz.ruanxy.java.balance.payload.vo.AccountRecordVO(r.id, a.id, )"
        + " from AccountRecordModel r left join r.account a")*/
    @Query(value = "select r.id, a.id as account_id, r.transaction_id, r.money, a.name as account_name, a.comment as account_comment, r.gmt_transaction from t_account_record as r join t_account as a on a.id = r.account_id where r.transaction_id = :transactionId ",nativeQuery = true)
    List<Map<String, Object>> findAccountRecordVo(@Param(value = "transactionId") long transactionId);
}
