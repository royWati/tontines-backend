package ekenya.co.ke.tontines.dao.repositories.accounting;


import ekenya.co.ke.tontines.dao.entitites.accounting.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountTypeRepository extends JpaRepository<AccountType, Long> {

    List<AccountType> findAllById(long id);

    List<AccountType> findAllByAccountPrefix(String prefix);
}
