package ekenya.co.ke.tontines.services;

import ekenya.co.ke.tontines.dao.entitites.MemberGroups;
import ekenya.co.ke.tontines.dao.entitites.accounting.AccountBalances;
import ekenya.co.ke.tontines.dao.entitites.accounting.AccountNumber;
import ekenya.co.ke.tontines.dao.entitites.accounting.RecordBalance;
import ekenya.co.ke.tontines.dao.wrappers.Response;
import ekenya.co.ke.tontines.dao.wrappers.UniversalResponse;
import ekenya.co.ke.tontines.dao.wrappers.accoutingdao.ExternalAccountDetailsWrapper;
import ekenya.co.ke.tontines.dao.wrappers.accoutingdao.RecordBalanceWrapper;
import ekenya.co.ke.tontines.services.accounting.AccountingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;
import java.util.List;

@Service
public class EntityManagementServiceV2Impl implements EntityManagementServiceV2 {

    @Autowired private AccountingService accountingService;
    @Autowired private EntityServicesRequirementsV1 entityServicesRequirementsV1;
    @Autowired private EntityServicesRequirementsV2 entityServicesRequirementsV2;
    @Override
    public UniversalResponse RECORD_EXTERNAL_ACCOUNT(RecordBalanceWrapper recordBalanceWrapper) {



        List<MemberGroups> memberGroupsList = entityServicesRequirementsV2.findMemberGroups(recordBalanceWrapper.getGroupId());

        if (memberGroupsList.size() > 0){
            RecordBalance recordBalance = new RecordBalance();
            recordBalance.setAccountName(recordBalanceWrapper.getAccountName());
            recordBalance.setAccountNumber(recordBalanceWrapper.getAccountNumber());
            MemberGroups m = memberGroupsList.get(0);
            recordBalance.setMemberGroup(m);

            ExternalAccountDetailsWrapper createdRecord = accountingService.recordExternalAccount(recordBalance,
                    recordBalanceWrapper.getAmount());

            return new UniversalResponse(new Response(200,"external account" +
                    " created successfully"),createdRecord);
        }else{
            return new UniversalResponse(new Response(404,"member group not found"));
        }

    }
}
