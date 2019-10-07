package ekenya.co.ke.tontines.dao.repositories.accounting;


import ekenya.co.ke.tontines.dao.entitites.accounting.TransactionsLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionLogRepository extends JpaRepository<TransactionsLog, Long> {

    List<TransactionsLog> findAllByTableNameAndUniqueTransactionId(String tableName, long id);
}
