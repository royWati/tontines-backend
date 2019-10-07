package ekenya.co.ke.tontines.dao.repositories.accounting;


import ekenya.co.ke.tontines.dao.entitites.accounting.AccountNumber;
import ekenya.co.ke.tontines.dao.entitites.accounting.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountNumbersRepository extends JpaRepository<AccountNumber, Long> {

    List<AccountNumber> findAllById(long id);

    List<AccountNumber> findAllByAccountType(AccountType accountType);

    List<AccountNumber> findAllByTableIdentifierAndTableName(long id, String tableName);
}
