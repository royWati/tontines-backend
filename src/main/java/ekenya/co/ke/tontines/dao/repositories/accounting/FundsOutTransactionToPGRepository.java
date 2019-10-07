package ekenya.co.ke.tontines.dao.repositories.accounting;


import ekenya.co.ke.tontines.dao.entitites.accounting.AccountNumber;
import ekenya.co.ke.tontines.dao.entitites.accounting.FundsOutTransactionToPG;
import ekenya.co.ke.tontines.dao.entitites.accounting.WalletServices;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FundsOutTransactionToPGRepository extends JpaRepository<FundsOutTransactionToPG, Long> {

    List<FundsOutTransactionToPG> findAllByAccountNumberAndAmountToPGAndProcessedAndWalletService(AccountNumber users,
                                                                                                  long amount,
                                                                                                  boolean processed,
                                                                                                  WalletServices
                                                                                                  walletServices);

    List<FundsOutTransactionToPG> findAllByAccountNumberAndWalletService(AccountNumber users, WalletServices walletServices);
}
