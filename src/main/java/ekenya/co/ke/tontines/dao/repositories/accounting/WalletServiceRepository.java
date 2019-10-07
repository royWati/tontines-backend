package ekenya.co.ke.tontines.dao.repositories.accounting;


import ekenya.co.ke.tontines.dao.entitites.accounting.WalletServices;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WalletServiceRepository extends JpaRepository<WalletServices, Long> {

    List<WalletServices> findAllByName(String name);
}
