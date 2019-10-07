package ekenya.co.ke.tontines.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import ekenya.co.ke.tontines.dao.wrappers.UniversalResponse;
import ekenya.co.ke.tontines.dao.wrappers.accoutingdao.ConfigWrapper;
import ekenya.co.ke.tontines.dao.wrappers.accoutingdao.ServicesWrapper;
import ekenya.co.ke.tontines.services.accounting.AccountingService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.logging.Logger;

@Service
public class AccountingConfiguration {

    private final static Logger logger = Logger.getLogger(AccountingConfiguration.class.getName());
    private static AccountingService  accountingService=null;

    @Value("${app-configs.AccountsConfig-directory}")
    public static String AccountsConfig;
    @Value("${app-configs.walletServicesWrapper-directory}")
    public static String walletServicesWrapper;

    public AccountingConfiguration(AccountingService accountingService) {
        this.accountingService = accountingService;
    }

    public static void ConfigureAccountingSystem(){
        Gson gson = new Gson();


        InputStream is = null;
        try {
            logger.info(AccountsConfig);
            is = new FileInputStream("configs/AccountsConfig.json");
            JsonParser jsonParser = new JsonParser();
            JsonElement jsonElement = jsonParser.parse(new InputStreamReader(Objects.requireNonNull(is), StandardCharsets.UTF_8));
            ConfigWrapper configWrapper = gson.fromJson(jsonElement,ConfigWrapper.class);

            String configString = new ObjectMapper().writeValueAsString(configWrapper);
            logger.info(configString);
            UniversalResponse universalResponseWrapper = accountingService.
                    createAccountConfiguration(configWrapper);
            logger.info(universalResponseWrapper.getResponse().getMessage());


        }catch (Exception e){
            logger.info("error while creating accounting elements");
            e.printStackTrace();
        }finally {
            try {
                is.close();
            } catch (Exception e) {

            }
        }
    }
    public static void ConfigureWalletServices() {

        Gson gson = new Gson();
        InputStream is = null;


        try {
            is = new FileInputStream("configs/walletServicesWrapper.json");
            JsonParser jsonParser = new JsonParser();
            JsonElement jsonElement = jsonParser.parse(new InputStreamReader(Objects.requireNonNull(is), StandardCharsets.UTF_8));
            ServicesWrapper servicesWrapper = gson.fromJson(jsonElement,ServicesWrapper.class);

            String serviceString = new ObjectMapper().writeValueAsString(servicesWrapper);
            logger.info(serviceString);

            accountingService.createWalletServices(servicesWrapper);
        }catch (Exception e){
            logger.info("error while creating wallet services elements");
            e.printStackTrace();
        }finally {
            try {
                is.close();
            } catch (Exception e) {

            }
        }
    }

    public static void walletConfiguration(){
        ConfigureAccountingSystem();
        ConfigureWalletServices();
    }

}
