package ekenya.co.ke.tontines.dao.repositories.accounting;


import ekenya.co.ke.tontines.dao.entitites.accounting.AccountNumber;
import ekenya.co.ke.tontines.dao.entitites.accounting.HoldingAccountsGeneralLedger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HoldingAccountsGLRepository extends JpaRepository<HoldingAccountsGeneralLedger, Long> {
    List<HoldingAccountsGeneralLedger> findAllByAccountNumber(AccountNumber accountNumber);

    List<HoldingAccountsGeneralLedger> findAllBySoftDelete(boolean status);
}
