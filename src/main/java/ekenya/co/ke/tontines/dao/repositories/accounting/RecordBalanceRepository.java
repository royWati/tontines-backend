package ekenya.co.ke.tontines.dao.repositories.accounting;

import ekenya.co.ke.tontines.dao.entitites.MemberGroups;
import ekenya.co.ke.tontines.dao.entitites.accounting.ExternalAccountTypes;
import ekenya.co.ke.tontines.dao.entitites.accounting.RecordBalance;
import ekenya.co.ke.tontines.dao.repositories.jpql.GetExternalGroupAccounts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RecordBalanceRepository extends JpaRepository<RecordBalance,Long> {

        String strGetGroupAccountDetails = "SELECT NEW ekenya.co.ke.tontines." +
                "dao.repositories.jpql.GetExternalGroupAccounts(" +
                "recordBalance," +
                "accountBalances) FROM RecordBalance recordBalance INNER JOIN AccountNumber accountNumber" +
                " ON recordBalance.id = accountNumber.tableIdentifier AND accountNumber.tableName='RecordBalance'" +
                " INNER JOIN AccountBalances accountBalances " +
                " ON accountNumber.id = accountBalances.accountNumber.id " +
                "WHERE recordBalance.memberGroup.id = :memberGroup AND recordBalance.accountTypes.id = :accountType ";


    @Query(strGetGroupAccountDetails)
    Page<GetExternalGroupAccounts> GET_GROUP_EXTENAL_ACCOUNT_BALANCES(@Param("memberGroup")
                                                                              long memberGroupId,
                                                                      @Param("accountType")
                                                                              long accountType,
                                                                      Pageable pageable);
}
