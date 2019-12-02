package ekenya.co.ke.tontines.services.accounting;


import ekenya.co.ke.tontines.dao.entitites.MemberGroups;
import ekenya.co.ke.tontines.dao.entitites.accounting.*;
import ekenya.co.ke.tontines.dao.repositories.jpql.GetExternalGroupAccounts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AccountingRequirementsService {

    /**
     * account balances
     */
    AccountBalances updateBalanceEntry(AccountBalances accountBalances);
    List<AccountBalances> getAccountBalanceByAccountNumber(AccountNumber accountNumber);
    List<AccountBalances> getAccountBalanceById(long id);
    AccountBalances getAccountBalance(AccountNumber accountNumber);

    /**
     * account mode
     */
    AccountMode updateAccountMode(AccountMode accountMode);
    List<AccountMode> findAccountMode(long id);
    List<AccountMode> findAccountMode(String mode);
    List<AccountMode> createAccountModes(List<AccountMode> modes);


    /**
     * accountType
     */
    AccountType updateAccountType(AccountType accountType);
    List<AccountType> findAccountTypeById(long id);
    List<AccountType> findAccountTypeByPrefix(String prefix);
    List<AccountType> createAccountTypes(List<AccountType> accountTypes);

    /**
     * account number
     */
    AccountNumber updateAccountNumber(AccountNumber accountNumber);
    List<AccountNumber> findAccountNumber(long id);
    List<AccountNumber> findAccountNumber(AccountType accountType);
    List<AccountNumber> findAccountNumber(long id, String tableName);
    /**
     *
     * @param generalLedger
     * @return
     */
    GeneralLedger updateGeneralLedger(GeneralLedger generalLedger);


    TransactionsLog updateTransactionLog(TransactionsLog transactionsLog);
    List<TransactionsLog> findTransactionLogByTableNameAndUniqueId(String name, long id);

    TransactionTypes updateTransactionType(TransactionTypes transactionTypes);
    List<TransactionTypes> getTransactionTypeById(long id);
    List<TransactionTypes> createTransactionTypes(List<TransactionTypes> transactionTypes);


    List<HoldingAccounts> createHoldingAccounts(List<HoldingAccounts> holdingAccounts);
    HoldingAccounts updateHoldingAccounts(HoldingAccounts holdingAccounts);
    List<HoldingAccounts> findAllHoldingAccounts();
    HoldingAccounts findHoldingAccountByPrefix(String prefix);
    List<HoldingAccounts> findAllHoldingAccountByPrefix(String prefix);
    List<HoldingAccounts> findHoldingAccountByName(String name);

    List<HoldingAccountsGeneralLedger> createHoldingAccGL(List<HoldingAccountsGeneralLedger> holdingAccountsGeneralLedgers);
    HoldingAccountsGeneralLedger updateHoldingAccGL(HoldingAccountsGeneralLedger holdingAccountsGeneralLedger);
    List<HoldingAccountsGeneralLedger> findHoldingAccGL(AccountNumber accountNumber);

    List<HoldingAccountsGeneralLedger> findAllHAGL();


    List<WalletServices> createWalletService(List<WalletServices> walletServices);
    WalletServices createWalletService(WalletServices walletServices);
    List<WalletServices> fetchAllWalletServices();
    List<WalletServiceTransactionDetails> createWalletServiceDetails(List<WalletServiceTransactionDetails> list);
    WalletServiceTransactionDetails createWalletServiceDetails(WalletServiceTransactionDetails walletServiceTransactionDetails);
    List<WalletServiceTransactionDetails> findWalletTransactionDetails(WalletServices walletServices);
    List<WalletServiceTransactionDetails> findWalletTransactionDetails(WalletServices walletServices,
                                                                       long totalCharge);
    long countWalletServices();
    long countWalletServiceDetails();

    List<WalletServices> findWalletService(String name);
    FundsOutTransactionToPG createFundsOutToPG(FundsOutTransactionToPG fundsOutTransactionToPG);
    List<FundsOutTransactionToPG> findFundsOutToPG(AccountNumber accountNumber, long Amount, boolean state, WalletServices walletServices);
    List<FundsOutTransactionToPG> findFundsOutTransaction(AccountNumber accountNumber, WalletServices walletServices);

    List<PaymentRecovery> retrieveUserPaymentRecoveryInfo(AccountNumber accountNumber);
    PaymentRecovery updatePaymentRecoveryInfo(PaymentRecovery paymentRecovery);



    ErroredTransactions writeErroredTransaction(ErroredTransactions erroredTransactions);

    List<ErroredTransactions> findAllUnfixedTransactions();

    Page<GeneralLedger> getAccountStatements(AccountNumber accountNumber, Pageable pageable);

    List<HoldingAccountsGeneralLedger> getAllHoldingAccountsGeneralLedger();


    RecordBalance createRecordBalance(RecordBalance recordBalance);

    Page<GetExternalGroupAccounts> getExternalGroupAccounts(MemberGroups memberGroupId,
                                                            ExternalAccountTypes accountType, Pageable pageable);
}
