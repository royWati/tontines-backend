package ekenya.co.ke.tontines.dao.repositories.accounting;


import ekenya.co.ke.tontines.dao.entitites.accounting.HoldingAccounts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HoldingAccountsRepository extends JpaRepository<HoldingAccounts, Long> {

    List<HoldingAccounts> findAllByAccountName(String name);
}
