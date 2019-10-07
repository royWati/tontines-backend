package ekenya.co.ke.tontines.dao.repositories.accounting;

import ekenya.co.ke.tontines.dao.entitites.accounting.RecordBalance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordBalanceRepository extends JpaRepository<RecordBalance,Long> {
}
