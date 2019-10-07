package ekenya.co.ke.tontines.dao.repositories.accounting;


import ekenya.co.ke.tontines.dao.entitites.accounting.WalletServiceTransactionDetails;
import ekenya.co.ke.tontines.dao.entitites.accounting.WalletServices;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WalletServiceTransactionDetailsRepository extends JpaRepository<WalletServiceTransactionDetails, Long> {

    List<WalletServiceTransactionDetails> findAllByWalletServices(WalletServices walletServices);

    List<WalletServiceTransactionDetails> findAllByWalletServicesAndTotalAmount(WalletServices walletServices,
                                                                                double totalAmount);
}
