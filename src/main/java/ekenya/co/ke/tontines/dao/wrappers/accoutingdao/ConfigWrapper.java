package ekenya.co.ke.tontines.dao.wrappers.accoutingdao;


import ekenya.co.ke.tontines.dao.entitites.accounting.AccountMode;
import ekenya.co.ke.tontines.dao.entitites.accounting.AccountType;
import ekenya.co.ke.tontines.dao.entitites.accounting.HoldingAccounts;
import ekenya.co.ke.tontines.dao.entitites.accounting.TransactionTypes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConfigWrapper {

    private List<TransactionTypes> transactionTypes;
    private List<AccountMode> accountModes;
    private List<AccountType> accountTypes;
    private List<HoldingAccounts> holdingAccounts;
    private List<HoldingAccounts> serviceAccounts;
}
