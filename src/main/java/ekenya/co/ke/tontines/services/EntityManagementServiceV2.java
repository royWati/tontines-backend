package ekenya.co.ke.tontines.services;

import ekenya.co.ke.tontines.dao.wrappers.UniversalResponse;
import ekenya.co.ke.tontines.dao.wrappers.accoutingdao.RecordBalanceWrapper;

/**
 * contains record balance
 */
public interface EntityManagementServiceV2 {


    UniversalResponse RECORD_EXTERNAL_ACCOUNT(RecordBalanceWrapper recordBalanceWrapper);
}
