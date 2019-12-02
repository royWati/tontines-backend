package ekenya.co.ke.tontines.services.accounting;


import ekenya.co.ke.tontines.dao.entitites.accounting.*;
import ekenya.co.ke.tontines.dao.wrappers.Response;
import ekenya.co.ke.tontines.dao.wrappers.UniversalResponse;
import ekenya.co.ke.tontines.dao.wrappers.accoutingdao.ConfigWrapper;
import ekenya.co.ke.tontines.dao.wrappers.accoutingdao.ExternalAccountDetailsWrapper;
import ekenya.co.ke.tontines.dao.wrappers.accoutingdao.ServicesWrapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

@Service
public class AccountingServiceImpl implements AccountingService {

    private final static Logger logger = Logger.getLogger(AccountingServiceImpl.class.getName());

    private final AccountingRequirementsService accountingRequirementsService;
    public AccountingServiceImpl(AccountingRequirementsService accountingRequirementsService) {
        this.accountingRequirementsService = accountingRequirementsService;
    }

    @Override
    public AccountNumber createAccountNumber(long uniqueId, String tableName, AccountType accountType) {

        AccountNumber accountNumber = new AccountNumber();

        AccountMode accountMode = accountingRequirementsService.findAccountMode("ACTIVE").get(0);

        Instant instant = Instant.now();
        long timeStampMillis = instant.toEpochMilli();
        logger.info(String.valueOf(timeStampMillis));
        StringBuilder accRefBuilder;
        accRefBuilder = new StringBuilder();
        accRefBuilder.append(accountType.getAccountPrefix()).append(timeStampMillis);

        String refNo = accRefBuilder.toString();
        /**
         * create account number
         */
        accountNumber.setAccountMode(accountMode);
        accountNumber.setTableIdentifier(uniqueId);
        accountNumber.setAccRefNumber(refNo);
        accountNumber.setTableName(tableName);
        accountNumber.setAccountType(accountType);
        AccountNumber  createdAccountNumber =  accountingRequirementsService.updateAccountNumber(accountNumber);

        logger.info("account created.."+refNo.substring(0,5)+"XXXXX");

        /**
         * create account balance at this point
         */


            AccountBalances accountBalances = createAccountBalance(createdAccountNumber);

            logger.info("account balance created for account "+refNo.substring(0,5)+"XXXXX");
            logger.info("available balance KES "+refNo.substring(0,5)+"XXXXX"+accountBalances.getAvailableBalance());

        if (!accountType.getAccountPrefix().equals("MA")){
            createHoldingAccGL(accountNumber);
            logger.info("general ledger created for account type..."+accountType.getAccountPrefix());
        }


        return createdAccountNumber;
    }

    @Override
    public List<AccountNumber> findAccountNumber(long id, String tableName) {
        return accountingRequirementsService.findAccountNumber(id, tableName);
    }

    @Override
    public AccountBalances geAccountBalance(AccountNumber accountNumber) {
        return accountingRequirementsService.getAccountBalance(accountNumber);
    }

    @Override
    public List<AccountType> findAccountType(String prefix) {
        return accountingRequirementsService.findAccountTypeByPrefix(prefix);
    }

    @Override
    public GeneralLedger updateGeneralLedger(AccountNumber accountNumber, TransactionsLog
                                              transactionsLog, String amount, TransactionTypes transactionType) {

        GeneralLedger generalLedger = new GeneralLedger();


        AccountBalances accountBalances = accountNumberBalanceInquiry(accountNumber);

        double amountGenerated = Double.parseDouble(amount);

        generalLedger.setActualBalanceBefore(accountBalances.getActualBalance());
        generalLedger.setAvailableBalanceBefore(accountBalances.getAvailableBalance());
        double actualBalanceAfter =0;
        double availableBalanceAfter =0;
        if (transactionType.getName().equals("DEBIT")){
             actualBalanceAfter = Double.parseDouble(accountBalances.getActualBalance())-amountGenerated;
             availableBalanceAfter = Double.parseDouble(accountBalances.getAvailableBalance())-amountGenerated;
        }else{
            actualBalanceAfter = Double.parseDouble(accountBalances.getActualBalance())+ amountGenerated;
            availableBalanceAfter = Double.parseDouble(accountBalances.getAvailableBalance()) + amountGenerated;
        }
        generalLedger.setAvailableBalanceAfter(DoubleConverterToString(availableBalanceAfter));
        generalLedger.setActualBalanceAfter(DoubleConverterToString(actualBalanceAfter));
        generalLedger.setAccountNumber(accountNumber);
        generalLedger.setTransactionType(transactionType);
        generalLedger.setTransactionSource(transactionsLog);
        generalLedger.setAmount(amount);

        /**
         * update the actual balance of the account number at this point..
         */




            accountBalances.setUpdatedOn(LocalDateTime.now());
            accountBalances.setAvailableBalance(DoubleConverterToString(availableBalanceAfter));
            accountBalances.setActualBalance(DoubleConverterToString(actualBalanceAfter));

            AccountBalances updatedAccountBalance = updateAccountBalance(accountBalances);
            logger.info("updating account balance for the account.."+updatedAccountBalance.getId());

        if(!accountNumber.getAccountType().getAccountPrefix().equals("MA")){
            HoldingAccountsGeneralLedger accountsGeneralLedger = accountingRequirementsService.findHoldingAccGL(accountNumber).get(0);
            HoldingAccountsGeneralLedger updatedHoldingAccount =new HoldingAccountsGeneralLedger();


            updatedHoldingAccount.setId(accountsGeneralLedger.getId());
            updatedHoldingAccount.setCreatedOn(accountsGeneralLedger.getCreatedOn());
            updatedHoldingAccount.setAccountNumber(accountsGeneralLedger.getAccountNumber());

            logger.info("general ledger transaction type..."+transactionType.getName());
            logger.info("general ledger account number..."+accountsGeneralLedger.getAccountNumber().getAccRefNumber());
            logger.info("general ledger credit balance..."+accountsGeneralLedger.getCreditBalance());
            logger.info("general ledger debit balance..."+accountsGeneralLedger.getDebitBalance());
            if (transactionType.getName().equals("DEBIT")){
                double debitAmount = Double.parseDouble(accountsGeneralLedger.getDebitBalance());
                logger.info("general ledger debit before..."+debitAmount);
                double total = debitAmount+ amountGenerated;
                logger.info("general ledger debit after..."+total);

                updatedHoldingAccount.setDebitBalance(DoubleConverterToString(total));
                updatedHoldingAccount.setCreditBalance(accountsGeneralLedger.getCreditBalance());
                updatedHoldingAccount.setUpdateOn(LocalDateTime.now());
                HoldingAccountsGeneralLedger holdingAccountsGeneralLedger=updateHoldingAccGL(updatedHoldingAccount);
                logger.info("holding general ledger updated..."+holdingAccountsGeneralLedger.getCreditBalance());

            }else{
                double creditAmount = Double.parseDouble(accountsGeneralLedger.getCreditBalance());
                logger.info("general ledger credit before..."+creditAmount+" ..."+
                        accountsGeneralLedger.getAccountNumber().getAccRefNumber());
                double total = creditAmount+ amountGenerated;
                logger.info("general ledger credit after..."+total+" ..."+
                        accountsGeneralLedger.getAccountNumber().getAccRefNumber());

                updatedHoldingAccount.setCreditBalance(DoubleConverterToString(total));
                updatedHoldingAccount.setDebitBalance(accountsGeneralLedger.getDebitBalance());
                updatedHoldingAccount.setUpdateOn(LocalDateTime.now());

                HoldingAccountsGeneralLedger holdingAccountsGeneralLedger=updateHoldingAccGL(updatedHoldingAccount);
                logger.info("holding general ledger updated..."+holdingAccountsGeneralLedger.getDebitBalance());

            }



        }


        return accountingRequirementsService.updateGeneralLedger(generalLedger);
    }

    private HoldingAccountsGeneralLedger updateHoldingAccGL(HoldingAccountsGeneralLedger updatedHoldingAccount) {
        return accountingRequirementsService.updateHoldingAccGL(updatedHoldingAccount);
    }


    public String DoubleConverterToString(double value){
        double round = Math.round(value * 100.0) / 100.0;
        return String.valueOf(round);
    }


    @Override
    public HoldingAccountsGeneralLedger createHoldingAccGL(AccountNumber accountNumber) {
        HoldingAccountsGeneralLedger holdingAccountsGeneralLedger = new HoldingAccountsGeneralLedger();
        holdingAccountsGeneralLedger.setAccountNumber(accountNumber);
        holdingAccountsGeneralLedger.setDebitBalance("0.0");
        holdingAccountsGeneralLedger.setCreditBalance("0.0");
        holdingAccountsGeneralLedger.setUpdateOn(LocalDateTime.now());

        return accountingRequirementsService.updateHoldingAccGL(holdingAccountsGeneralLedger);
    }

    @Override
    public AccountBalances createAccountBalance(AccountNumber accountNumber) {
        AccountBalances accountBalances = new AccountBalances();
        accountBalances.setAccountNumber(accountNumber);
        accountBalances.setActualBalance("0");
        accountBalances.setAvailableBalance("0");
        accountBalances.setUpdatedOn(LocalDateTime.now());

        return accountingRequirementsService.updateBalanceEntry(accountBalances);
    }

    @Override
    public AccountBalances updateAccountBalance(AccountBalances accountBalances) {
        return accountingRequirementsService.updateBalanceEntry(accountBalances);
    }

    @Override
    public AccountBalances accountNumberBalanceInquiry(AccountNumber accountNumber) {
        return accountingRequirementsService.getAccountBalance(accountNumber);
    }

    @Override
    public TransactionsLog createTransactionLog(long uniqueId, String tableName, String narration) {
        TransactionsLog transactionsLog = new TransactionsLog();
        transactionsLog.setNarration(narration);
        transactionsLog.setTableName(tableName);
        transactionsLog.setUniqueTransactionId(uniqueId);
        return accountingRequirementsService.updateTransactionLog(transactionsLog);
    }

    @Override
    public UniversalResponse createAccountConfiguration(ConfigWrapper configWrapper) {

        UniversalResponse universalResponseWrapper = new UniversalResponse();
        ConfigWrapper configWrapper1 = new ConfigWrapper();

        logger.info("holding accounts total..."+accountingRequirementsService.findAllHoldingAccounts().size());
        logger.info("account modes..."+configWrapper.getAccountModes().size());

        if (accountingRequirementsService.findAllHoldingAccounts().size() == 0){

            configWrapper1.setAccountModes(accountingRequirementsService.createAccountModes(
                    configWrapper.getAccountModes()));
            configWrapper1.setAccountTypes(accountingRequirementsService.createAccountTypes(configWrapper.getAccountTypes()));
            configWrapper1.setTransactionTypes(accountingRequirementsService.createTransactionTypes(configWrapper.getTransactionTypes()));
            List<HoldingAccounts> holdingAccountsList = accountingRequirementsService.createHoldingAccounts(configWrapper.getHoldingAccounts());


            System.out.println("holding accounts..."+holdingAccountsList.size());
            holdingAccountsList.forEach(holdingAccounts -> {
                logger.info("holder account id "+holdingAccounts.getAccountType().getId());
                AccountType accountType = accountingRequirementsService.
                        findAccountTypeById(holdingAccounts.getAccountType().getId()).get(0);
                /**
                 * create account numbers
                 */
                AccountNumber acc = createAccountNumber(holdingAccounts.getId(),
                        HoldingAccounts.class.getSimpleName(),accountType);
                logger.info("account number created..."+acc.getId());
            });
            configWrapper1.setHoldingAccounts(holdingAccountsList);


            universalResponseWrapper.setResponse(new Response(200,"new accounting configuration set"));
            universalResponseWrapper.setData(configWrapper1);
        }else{

            List<HoldingAccounts> allHoldingAccounts = accountingRequirementsService.findAllHoldingAccounts();

            for(HoldingAccounts holdingAccounts : configWrapper.getHoldingAccounts()){

                boolean found = false;

                for(HoldingAccounts presentAccount : allHoldingAccounts){
                    if (holdingAccounts.getAccountName().equals(presentAccount.getAccountName())){
                        found = true;
                    }
                }

                if (!found){

                    accountingRequirementsService.findAccountTypeById(holdingAccounts.
                            getAccountType().getId()).forEach(accountType -> {
                        logger.info("account type prefix....>"+accountType.getAccountPrefix());
                        logger.info("account type name....>"+accountType.getAccountName());
                    });

                    AccountType accountType = accountingRequirementsService.
                            findAccountTypeById(holdingAccounts.getAccountType().getId()).get(0);

                            accountingRequirementsService.updateHoldingAccounts(holdingAccounts);

                    HoldingAccounts newHoldingAccount = new HoldingAccounts();
                    newHoldingAccount.setAccountName(holdingAccounts.getAccountName());
                    newHoldingAccount.setAccountType(accountType);
                    newHoldingAccount.setId(holdingAccounts.getId());

                    HoldingAccounts holdingAccounts1 = accountingRequirementsService.updateHoldingAccounts(
                            newHoldingAccount
                    );

                    /**
                     * create account number for the holding account
                     */




                    logger.info("holding account id..."+holdingAccounts1.getId());
                    logger.info("holding name id..."+holdingAccounts1.getAccountName());
                    logger.info("account type id..."+accountType.getId());
                    logger.info("account type prefix..."+accountType.getAccountPrefix());
                    logger.info("account type prefix..."+accountType.getAccountName());

                    AccountNumber acc = createAccountNumber(holdingAccounts1.getId(),
                            HoldingAccounts.class.getSimpleName(),accountType);
                    logger.info("account number created..."+acc.getId());
                }
            }

            universalResponseWrapper.setResponse(new Response(200,
                    "accounting configuration already exist"));


        }
        return universalResponseWrapper;
    }

    @Override
    public ExternalAccountDetailsWrapper recordExternalAccount(RecordBalance recordBalance, String Amount) {

        RecordBalance createRecord = accountingRequirementsService.createRecordBalance(recordBalance);

        AccountType accountType = accountingRequirementsService.findAccountTypeByPrefix("EXA").get(0);

        AccountNumber accountNumber = createAccountNumber(createRecord.getId(),
                RecordBalance.class.getSimpleName(),accountType);

        AccountBalances accountBalances = accountingRequirementsService.getAccountBalance(accountNumber);
        accountBalances.setAvailableBalance(Amount);

        AccountBalances ac = accountingRequirementsService.updateBalanceEntry(accountBalances);

        logger.info(ac.getAvailableBalance()+" has been updated to acc "+accountNumber.getId());

        ExternalAccountDetailsWrapper wrapper = new ExternalAccountDetailsWrapper();

        wrapper.setExternalAccount(createRecord);
        wrapper.setBalanceDetails(ac);

        return wrapper;
    }

    public void createWalletServices(ServicesWrapper servicesWrapper) {

        if (servicesWrapper.getWalletServices().size() > accountingRequirementsService.countWalletServices()){
            logger.info("creating wallet  services...");

            /**
             * fetch all wallet services present
             */

            List<WalletServices> walletServiceList = accountingRequirementsService.fetchAllWalletServices();

            List<WalletServices> configList = servicesWrapper.getWalletServices();

            AccountType accountType = accountingRequirementsService.findAccountTypeByPrefix("SA").get(0);
            for (WalletServices configWalletService : configList){

                boolean found = false;

                for (WalletServices presentWalletService: walletServiceList){
                    if (configWalletService.getName().equals(presentWalletService.getName())){
                        found = true;
                    }
                }

                if (!found){
                    WalletServices walletServices = accountingRequirementsService.createWalletService(
                            configWalletService
                    );

                    createAccountNumber(walletServices.getId(),WalletServices.class.getSimpleName(),
                            accountType);
                }
            }

//            List<WalletServices> list = accountingRequirementsService.
//                    createWalletService(servicesWrapper.getWalletServices());
//            list.forEach(walletServices -> createAccountNumber(walletServices.getId(),
//                    WalletServices.class.getSimpleName(),accountType));
        }else{
            logger.info("wallet  services exists...");
        }

        if (servicesWrapper.getDetails().size() > accountingRequirementsService.countWalletServiceDetails()){
            logger.info("creating wallet  service details...");
            accountingRequirementsService.createWalletServiceDetails(servicesWrapper.getDetails());
        }else{
            logger.info("wallet  service details exists...");
        }

    }

    @Override
    public void initiateLedgerTransactionLog(AccountNumber creditAccount,
                                             AccountNumber debitAccount,
                                             TransactionsLog transactionsLog,String amount) {

        TransactionTypes debitTransaction = accountingRequirementsService.getTransactionTypeById(1).get(0);
        TransactionTypes creditTransaction = accountingRequirementsService.getTransactionTypeById(2).get(0);

        updateGeneralLedger(debitAccount,transactionsLog,amount,debitTransaction);
        updateGeneralLedger(creditAccount,transactionsLog,amount,creditTransaction);

    }


}
