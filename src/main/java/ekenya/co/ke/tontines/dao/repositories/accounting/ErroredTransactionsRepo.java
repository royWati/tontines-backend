package ekenya.co.ke.tontines.dao.repositories.accounting;

import ekenya.co.ke.tontines.dao.entitites.accounting.ErroredTransactions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ErroredTransactionsRepo extends JpaRepository<ErroredTransactions, Long> {
    List<ErroredTransactions> findAllByTransactionId(long id);

    List<ErroredTransactions> findAllByFixed(boolean fixed);
}
