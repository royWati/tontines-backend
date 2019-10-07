package ekenya.co.ke.tontines.services.accounting;


import ekenya.co.ke.tontines.dao.entitites.accounting.*;
import ekenya.co.ke.tontines.dao.repositories.accounting.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountingRequirementsServiceImpl implements AccountingRequirementsService {

    private final AccountModeRepository accountModeRepository;
    private final AccountBalancesRepository accountBalancesRepository;
    private final AccountTypeRepository accountTypeRepository;
    private final AccountNumbersRepository accountNumbersRepository;
    private final TransactionLogRepository transactionLogRepository;
    private final GeneralLedgerRepository generalLedgerRepository;
    private final TransactionTypeRepository transactionTypeRepository;
    private final HoldingAccountsRepository holdingAccountsRepository;
    private final HoldingAccountsGLRepository holdingAccountsGLRepository;
    private final WalletServiceRepository walletServiceRepository;
    private final WalletServiceTransactionDetailsRepository walletServiceTransactionDetailsRepository;
    private final FundsOutTransactionToPGRepository fundsOutTransactionToPGRepository;
    private final PaymentRecoveryRepository paymentRecoveryRepository;
  //  private final BillsRepository billsRepository;
    private final ErroredTransactionsRepo erroredTransactionsRepo;

    private final RecordBalanceRepository recordBalanceRepository;

    public AccountingRequirementsServiceImpl(AccountModeRepository accountModeRepository,
                                             AccountBalancesRepository accountBalancesRepository,
                                             AccountTypeRepository accountTypeRepository,
                                             AccountNumbersRepository accountNumbersRepository,
                                             TransactionLogRepository transactionLogRepository,
                                             GeneralLedgerRepository generalLedgerRepository,
                                             TransactionTypeRepository transactionTypeRepository,
                                             HoldingAccountsRepository holdingAccountsRepository,
                                             HoldingAccountsGLRepository holdingAccountsGLRepository,
                                             WalletServiceRepository walletServiceRepository,
                                             WalletServiceTransactionDetailsRepository walletServiceTransactionDetailsRepository,
                                             FundsOutTransactionToPGRepository fundsOutTransactionToPGRepository,
                                             PaymentRecoveryRepository paymentRecoveryRepository,
                                             ErroredTransactionsRepo erroredTransactionsRepo,
                                             RecordBalanceRepository recordBalanceRepository) {
        this.accountModeRepository = accountModeRepository;
        this.accountBalancesRepository = accountBalancesRepository;
        this.accountTypeRepository = accountTypeRepository;
        this.accountNumbersRepository = accountNumbersRepository;
        this.transactionLogRepository = transactionLogRepository;
        this.generalLedgerRepository = generalLedgerRepository;
        this.transactionTypeRepository = transactionTypeRepository;
        this.holdingAccountsRepository = holdingAccountsRepository;
        this.holdingAccountsGLRepository = holdingAccountsGLRepository;
        this.walletServiceRepository = walletServiceRepository;
        this.walletServiceTransactionDetailsRepository = walletServiceTransactionDetailsRepository;
        this.fundsOutTransactionToPGRepository = fundsOutTransactionToPGRepository;
        this.paymentRecoveryRepository = paymentRecoveryRepository;
    //    this.billsRepository = billsRepository;
        this.erroredTransactionsRepo = erroredTransactionsRepo;
        this.recordBalanceRepository = recordBalanceRepository;
    }

    @Override
    public AccountBalances updateBalanceEntry(AccountBalances accountBalances) {
        return accountBalancesRepository.save(accountBalances);
    }

    @Override
    public List<AccountBalances> getAccountBalanceByAccountNumber(AccountNumber accountNumber) {
        return accountBalancesRepository.findAllByAccountNumber(accountNumber);
    }

    @Override
    public AccountBalances getAccountBalance(AccountNumber accountNumber) {
        return getAccountBalanceByAccountNumber(accountNumber).get(0);
    }

    @Override
    public List<AccountBalances> getAccountBalanceById(long id) {
        return accountBalancesRepository.findAllById(id);
    }

    @Override
    public AccountMode updateAccountMode(AccountMode accountMode) {
        return accountModeRepository.save(accountMode);
    }

    @Override
    public List<AccountMode> findAccountMode(long id) {
        return accountModeRepository.findAllById(id);
    }

    @Override
    public List<AccountMode> findAccountMode(String mode) {
        return accountModeRepository.findAllByMode(mode);
    }

    @Override
    public List<AccountMode> createAccountModes(List<AccountMode> modes) {
        return accountModeRepository.saveAll(modes);
    }


    @Override
    public AccountType updateAccountType(AccountType accountType) {
        return accountTypeRepository.save(accountType);
    }

    @Override
    public List<AccountType> findAccountTypeById(long id) {
        return accountTypeRepository.findAllById(id);
    }

    @Override
    public List<AccountType> findAccountTypeByPrefix(String prefix) {
        return accountTypeRepository.findAllByAccountPrefix(prefix);
    }

    @Override
    public List<AccountType> createAccountTypes(List<AccountType> accountTypes) {
        return accountTypeRepository.saveAll(accountTypes);
    }

    @Override
    public AccountNumber updateAccountNumber(AccountNumber accountNumber) {
        return accountNumbersRepository.save(accountNumber);
    }

    @Override
    public List<AccountNumber> findAccountNumber(long id) {
        return accountNumbersRepository.findAllById(id);
    }

    @Override
    public List<AccountNumber> findAccountNumber(AccountType accountType) {
        return accountNumbersRepository.findAllByAccountType(accountType);
    }

    @Override
    public List<AccountNumber> findAccountNumber(long id, String tableName) {
        return accountNumbersRepository.findAllByTableIdentifierAndTableName(id, tableName);
    }

    @Override
    public GeneralLedger updateGeneralLedger(GeneralLedger generalLedger) {
        return generalLedgerRepository.save(generalLedger);
    }

    @Override
    public TransactionsLog updateTransactionLog(TransactionsLog transactionsLog) {
        return transactionLogRepository.save(transactionsLog);
    }

    @Override
    public List<TransactionsLog> findTransactionLogByTableNameAndUniqueId(String name, long id) {
        return transactionLogRepository.findAllByTableNameAndUniqueTransactionId(name, id);
    }

    @Override
    public TransactionTypes updateTransactionType(TransactionTypes transactionTypes) {
        return transactionTypeRepository.save(transactionTypes);
    }

    @Override
    public List<TransactionTypes> getTransactionTypeById(long id) {
        return transactionTypeRepository.findAllById(id);
    }

    @Override
    public List<TransactionTypes> createTransactionTypes(List<TransactionTypes> transactionTypes) {
        return transactionTypeRepository.saveAll(transactionTypes);
    }

    @Override
    public List<HoldingAccounts> createHoldingAccounts(List<HoldingAccounts> holdingAccounts) {
        return holdingAccountsRepository.saveAll(holdingAccounts);
    }

    @Override
    public HoldingAccounts updateHoldingAccounts(HoldingAccounts holdingAccounts) {
        return holdingAccountsRepository.save(holdingAccounts);
    }

    @Override
    public List<HoldingAccounts> findAllHoldingAccounts() {
        return holdingAccountsRepository.findAll();
    }

    @Override
    public HoldingAccounts findHoldingAccountByPrefix(String prefix) {
        List<HoldingAccounts> holdingAccountsList = new ArrayList<>();
        findAllHoldingAccounts().forEach(holdingAccounts -> {
            if (holdingAccounts.getAccountType().getAccountPrefix().equals(prefix)){
                holdingAccountsList.add(holdingAccounts);
            }
        });

        return holdingAccountsList.get(0);
    }

    @Override
    public List<HoldingAccounts> findAllHoldingAccountByPrefix(String prefix) {
        List<HoldingAccounts> holdingAccountsList = new ArrayList<>();
        findAllHoldingAccounts().forEach(holdingAccounts -> {
            if (holdingAccounts.getAccountType().getAccountPrefix().equals(prefix)){
                holdingAccountsList.add(holdingAccounts);
            }
        });

        return holdingAccountsList;
    }

    @Override
    public List<HoldingAccounts> findHoldingAccountByName(String name) {
        return holdingAccountsRepository.findAllByAccountName(name);
    }

    @Override
    public List<HoldingAccountsGeneralLedger> createHoldingAccGL(List<HoldingAccountsGeneralLedger> holdingAccountsGeneralLedgers) {

        return holdingAccountsGLRepository.saveAll(holdingAccountsGeneralLedgers);
    }

    @Override
    public HoldingAccountsGeneralLedger updateHoldingAccGL(HoldingAccountsGeneralLedger holdingAccountsGeneralLedger) {
        return holdingAccountsGLRepository.save(holdingAccountsGeneralLedger);
    }

    @Override
    public List<HoldingAccountsGeneralLedger> findHoldingAccGL(AccountNumber accountNumber) {
        return holdingAccountsGLRepository.findAllByAccountNumber(accountNumber);
    }

    @Override
    public List<HoldingAccountsGeneralLedger> findAllHAGL() {
        return holdingAccountsGLRepository.findAllBySoftDelete(false);
    }

    @Override
    public List<WalletServices> createWalletService(List<WalletServices> walletServices) {
        return walletServiceRepository.saveAll(walletServices);
    }

    @Override
    public WalletServices createWalletService(WalletServices walletServices) {
        return walletServiceRepository.save(walletServices);
    }

    @Override
    public List<WalletServices> fetchAllWalletServices() {
        return walletServiceRepository.findAll();
    }

    @Override
    public List<WalletServiceTransactionDetails> createWalletServiceDetails(List<WalletServiceTransactionDetails> list) {
        return walletServiceTransactionDetailsRepository.saveAll(list);
    }

    @Override
    public WalletServiceTransactionDetails createWalletServiceDetails(WalletServiceTransactionDetails walletServiceTransactionDetails) {
        return walletServiceTransactionDetailsRepository.save(walletServiceTransactionDetails);
    }

    @Override
    public List<WalletServiceTransactionDetails> findWalletTransactionDetails(WalletServices walletServices) {
        return walletServiceTransactionDetailsRepository.findAllByWalletServices(walletServices);
    }

    @Override
    public List<WalletServiceTransactionDetails> findWalletTransactionDetails(WalletServices walletServices, long totalCharge) {
        return walletServiceTransactionDetailsRepository.findAllByWalletServicesAndTotalAmount(
                walletServices,totalCharge
        );
    }

    @Override
    public long countWalletServices() {
        return walletServiceRepository.count();
    }

    @Override
    public long countWalletServiceDetails() {
        return walletServiceTransactionDetailsRepository.count();
    }

    @Override
    public List<WalletServices> findWalletService(String name) {
        return walletServiceRepository.findAllByName(name);
    }

    @Override
    public FundsOutTransactionToPG createFundsOutToPG(FundsOutTransactionToPG fundsOutTransactionToPG) {
        return fundsOutTransactionToPGRepository.save(fundsOutTransactionToPG);
    }

    @Override
    public List<FundsOutTransactionToPG> findFundsOutToPG(AccountNumber accountNumber, long Amount, boolean state,
                                                          WalletServices walletServices) {
        return fundsOutTransactionToPGRepository.
                findAllByAccountNumberAndAmountToPGAndProcessedAndWalletService(accountNumber,
                Amount, state,walletServices);
    }

    @Override
    public List<FundsOutTransactionToPG> findFundsOutTransaction(AccountNumber accountNumber, WalletServices walletServices) {
        return fundsOutTransactionToPGRepository.findAllByAccountNumberAndWalletService(accountNumber, walletServices);
    }

    @Override
    public List<PaymentRecovery> retrieveUserPaymentRecoveryInfo(AccountNumber accountNumber) {
        return paymentRecoveryRepository.findAllByAccountNumber(accountNumber);
    }

    @Override
    public PaymentRecovery updatePaymentRecoveryInfo(PaymentRecovery paymentRecovery) {
        return paymentRecoveryRepository.save(paymentRecovery);
    }



    @Override
    public ErroredTransactions writeErroredTransaction(ErroredTransactions erroredTransactions) {

        List<ErroredTransactions> list = erroredTransactionsRepo.
                findAllByTransactionId(erroredTransactions.getTransactionId());

        if (list.size() > 0){
            return erroredTransactionsRepo.save(erroredTransactions);
        }else{
            return list.get(0);
        }

    }

    @Override
    public List<ErroredTransactions> findAllUnfixedTransactions() {
        return erroredTransactionsRepo.findAllByFixed(false);
    }

    @Override
    public Page<GeneralLedger> getAccountStatements(AccountNumber accountNumber, Pageable pageable) {
        return generalLedgerRepository.findAllByAccountNumber(accountNumber,pageable);
    }

    @Override
    public List<HoldingAccountsGeneralLedger> getAllHoldingAccountsGeneralLedger() {
        return holdingAccountsGLRepository.findAllBySoftDelete(false);
    }

    @Override
    public RecordBalance createRecordBalance(RecordBalance recordBalance) {
        return recordBalanceRepository.save(recordBalance);
    }


}
