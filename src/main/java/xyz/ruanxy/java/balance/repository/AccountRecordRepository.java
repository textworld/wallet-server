package xyz.ruanxy.java.balance.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import xyz.ruanxy.java.balance.model.AccountModel;
import xyz.ruanxy.java.balance.model.AccountRecordModel;
import xyz.ruanxy.java.balance.model.TransactionModel;
import xyz.ruanxy.java.balance.payload.vo.AccountRecordVO;
import xyz.ruanxy.java.balance.payload.vo.TransactionVO;

@Repository
public interface AccountRecordRepository extends JpaRepository<AccountRecordModel, Long> {
    AccountRecordModel findByAccountAndTransaction(AccountModel accountModel, TransactionModel transactionModel);

//    @Query(value = "SELECT new xyz.ruanxy.java.balance.payload.vo.AccountRecordVO(r.id, a.id, a.name) from AccountRecordModel r JOIN r.account a where r.transaction.id = :transactionId")
//    List<AccountRecordVO>  getAllAccounts(@Param(value = "transactionId") long transactionId);

}
