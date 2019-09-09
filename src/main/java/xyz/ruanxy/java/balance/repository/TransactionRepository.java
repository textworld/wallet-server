package xyz.ruanxy.java.balance.repository;

import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import xyz.ruanxy.java.balance.model.AccountRecordModel;
import xyz.ruanxy.java.balance.model.TransactionModel;
import xyz.ruanxy.java.balance.payload.vo.AccountRecordVO;
import xyz.ruanxy.java.balance.payload.vo.TransactionVO;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionModel, Long> {
    @Query(value = "select r.id, a.id as account_id, r.transaction_id, r.money, a.name as account_name, a.comment as account_comment, r.gmt_transaction from t_account_record as r join t_account as a on a.id = r.account_id where r.transaction_id = :transactionId ",nativeQuery = true)
    List<Map<String, Object>> findAccountRecordVo(@Param(value = "transactionId") long transactionId);

    @Query(value = "select r.id, a.id as account_id, r.transaction_id, r.money, a.name as account_name, a.comment as account_comment, r.gmt_transaction \n"
        + "from t_account_record as r right join t_account as a on a.id = r.account_id where r.transaction_id = :transactionId or r.transaction_id is null;", nativeQuery = true)
    List<Map<String, Object>> findAlAccountRecord(@Param(value = "transactionId") long transactionId);
    @Query(value = "SELECT new xyz.ruanxy.java.balance.payload.vo.TransactionVO(r.transaction.id, sum(r.money),a.type) from AccountRecordModel r JOIN r.account a where r.transaction.id = :transactionId group by a.type")
    List<TransactionVO>  calculate(@Param(value = "transactionId") long transactionId);
    //@Query(value =" select sum(r.money) as sum, a.type from t_account_record as r join t_account as a on r.account_id = a.id where r.transaction_id = :transactionId GROUP BY a.type;", nativeQuery = true)
//    @Query(value = "SELECT a from AccountRecordModel r JOIN r.Account a")
//    List<AccountRecordModel> cal(@Param(value = "transactionId") long transactionId);
}
