package ekenya.co.ke.tontines.dao.wrappers.accoutingdao;


import ekenya.co.ke.tontines.dao.entitites.accounting.WalletServiceTransactionDetails;
import ekenya.co.ke.tontines.dao.entitites.accounting.WalletServices;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServicesWrapper {

    private List<WalletServices> walletServices;
    private List<WalletServiceTransactionDetails> details;
}
