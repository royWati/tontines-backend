package ekenya.co.ke.tontines.dao.wrappers.accoutingdao;

import ekenya.co.ke.tontines.dao.entitites.accounting.AccountBalances;
import ekenya.co.ke.tontines.dao.entitites.accounting.GeneralLedger;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountStatementsWrapper {

    private AccountBalances accountDetails;
    private Page<GeneralLedger> transactionStatements;
}
