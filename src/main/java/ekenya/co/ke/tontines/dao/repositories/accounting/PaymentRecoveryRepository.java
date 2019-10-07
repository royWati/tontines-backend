package ekenya.co.ke.tontines.dao.repositories.accounting;


import ekenya.co.ke.tontines.dao.entitites.accounting.AccountNumber;
import ekenya.co.ke.tontines.dao.entitites.accounting.PaymentRecovery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRecoveryRepository extends JpaRepository<PaymentRecovery, Long> {

    List<PaymentRecovery> findAllByAccountNumber(AccountNumber users);
}
