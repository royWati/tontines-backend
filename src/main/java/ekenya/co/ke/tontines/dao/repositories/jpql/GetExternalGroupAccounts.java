package ekenya.co.ke.tontines.dao.repositories.jpql;

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
public class GetExternalGroupAccounts {
    private RecordBalance accountProfile;
    private AccountBalances accountDetails;
}
