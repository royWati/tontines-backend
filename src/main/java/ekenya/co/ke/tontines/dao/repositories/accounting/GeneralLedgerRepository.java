package ekenya.co.ke.tontines.dao.repositories.accounting;


import ekenya.co.ke.tontines.dao.entitites.accounting.AccountNumber;
import ekenya.co.ke.tontines.dao.entitites.accounting.GeneralLedger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface GeneralLedgerRepository extends JpaRepository<GeneralLedger, Long>,
        PagingAndSortingRepository<GeneralLedger, Long> {

    Page<GeneralLedger> findAllByAccountNumber(AccountNumber accountNumber, Pageable pageable);
}
