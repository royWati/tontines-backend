package ekenya.co.ke.tontines.services.accounting;



import ekenya.co.ke.tontines.dao.entitites.accounting.*;
import ekenya.co.ke.tontines.dao.wrappers.UniversalResponse;
import ekenya.co.ke.tontines.dao.wrappers.accoutingdao.ConfigWrapper;
import ekenya.co.ke.tontines.dao.wrappers.accoutingdao.ExternalAccountDetailsWrapper;
import ekenya.co.ke.tontines.dao.wrappers.accoutingdao.ServicesWrapper;

import java.util.List;

public interface AccountingService {

    /**
     * - creating account numbers
     * - updating ledger
     * - create initial account balance for an the account number
     * * - update account balance for a specific account number
     * - get balance of a specific account number
     * - update transaction logs of all transactions taking place
     */

    AccountNumber createAccountNumber(long uniqueId, String tableName, AccountType accountType);
    List<AccountNumber> findAccountNumber(long id, String tableName);
    AccountBalances geAccountBalance(AccountNumber accountNumber);
    List<AccountType> findAccountType(String prefix);
    GeneralLedger updateGeneralLedger(AccountNumber accountNumber, TransactionsLog
            transactionsLog, String amount, TransactionTypes transactionType);
    AccountBalances createAccountBalance(AccountNumber accountNumber);
    HoldingAccountsGeneralLedger createHoldingAccGL(AccountNumber accountNumber);
    AccountBalances updateAccountBalance(AccountBalances accountBalances);
    AccountBalances accountNumberBalanceInquiry(AccountNumber accountNumber);
    TransactionsLog createTransactionLog(long uniqueId, String tableName, String narration);

    UniversalResponse createAccountConfiguration(ConfigWrapper configWrapper);

    ExternalAccountDetailsWrapper recordExternalAccount(RecordBalance recordBalance, String Amount);

    void createWalletServices(ServicesWrapper servicesWrapper);

    void initiateLedgerTransactionLog(AccountNumber creditAccount,AccountNumber debitAccount,
                                      TransactionsLog transactionsLog,String amount);
//    UniversalResponseWrapper createHoldingAccountsConfiguration(List<HoldingAccounts> holdingAccounts);
//
//    void initiateLedgerOperationForCreditAccounts(String tableName, long uniqueId, String narration,
//                                                  SuccessfulTransactionsFromPG successfulTransactionsFromPG,
//                                                  CreditCommissionStructures creditCommissionStructures,
//                                                  List<ActualCreditedAmountsForMembers> actualAccountsBalancesCredited);
//
//    HoldingAccountsGeneralLedger updateHoldingAccGL(HoldingAccountsGeneralLedger holdingAccountsGeneralLedger);
//
//    void initiateLedgerOperationForDebitAccounts(AccountNumber accountNumber,
//                                                 SuccessfulTransactionsFromPG successfulTransactionsFromPG, long uniqueId);
//
//
//    void createWalletServices(ServicesWrapper servicesWrapper);
//
//    WalletServices findWalletServiceByName(String name);
//    long findTotalWalletServiceByName(String name);
//
//    List<WalletServiceTransactionDetails> findWalletServiceDetails(WalletServices walletServices);
//
//    boolean similarTransactionUnderWay(Users users, long amount, WalletServices walletServices);
//
//    List<FundsOutTransactionToPG> findFundsOutTransaction(Users users, long amount, WalletServices walletServices);
//
//    FundsOutTransactionToPG findFundsOutTransaction(Users users, WalletServices walletServices);
//
//    FundsOutTransactionToPG updateFundsOutTransactionStatus(FundsOutTransactionToPG fundsOutTransactionToPG);
//
//    int initiatiateWalletServiceTransaction(Users users, long amount, long charges, WalletServices walletServices);
//    int initiatiateWalletServiceTransaction(Users users, long amount, WalletServices walletServices, String recipient);
//
//    void initiateReverseTransaction(FundsOutTransactionToPG fundsOutTransactionToPG);
//
//    void autoRecoverLoan(PaymentRecovery paymentRecovery, AccountNumber walletHoldingAccount,
//                         AccountNumber memberAccount,
//                         boolean expiredSubscription, String tableName, long uniqueId);
//
//    void writeErrorTransaction(ErroredTransactions erroredTransactions);
//
//    void  TransactionBoundErroredTransactionProcessing();
//
//
//    UniversalResponseWrapper getMemberWalletDetails(long id);
//
//    UniversalResponseWrapper getMemberWalletStatement(long id, int page, int size);
//
//    UniversalResponseWrapper holdingAccountsGeneralLedgers();

}
