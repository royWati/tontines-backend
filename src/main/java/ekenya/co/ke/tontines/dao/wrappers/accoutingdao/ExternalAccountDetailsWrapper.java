package ekenya.co.ke.tontines.dao.wrappers.accoutingdao;

import ekenya.co.ke.tontines.dao.entitites.accounting.AccountBalances;
import ekenya.co.ke.tontines.dao.entitites.accounting.RecordBalance;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExternalAccountDetailsWrapper {

    private RecordBalance externalAccount;
    private AccountBalances balanceDetails;
}
