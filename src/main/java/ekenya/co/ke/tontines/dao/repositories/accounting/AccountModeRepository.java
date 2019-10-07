package ekenya.co.ke.tontines.dao.repositories.accounting;


import ekenya.co.ke.tontines.dao.entitites.accounting.AccountMode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountModeRepository extends JpaRepository<AccountMode, Long> {

    List<AccountMode> findAllById(long id);

    List<AccountMode> findAllByMode(String mode);
}
