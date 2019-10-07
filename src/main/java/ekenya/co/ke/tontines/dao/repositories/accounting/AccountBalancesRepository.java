package ekenya.co.ke.tontines.dao.repositories.accounting;


import ekenya.co.ke.tontines.dao.entitites.accounting.AccountBalances;
import ekenya.co.ke.tontines.dao.entitites.accounting.AccountNumber;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountBalancesRepository extends JpaRepository<AccountBalances, Long> {


    List<AccountBalances> findAllByAccountNumber(AccountNumber accountNumber);

    List<AccountBalances> findAllById(long id);
}
