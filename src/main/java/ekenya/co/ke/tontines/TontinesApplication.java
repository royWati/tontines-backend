package ekenya.co.ke.tontines;

import ekenya.co.ke.tontines.configs.AccountingConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class TontinesApplication {

    public static void main(String[] args) {
        SpringApplication.run(TontinesApplication.class, args);

        AccountingConfiguration.walletConfiguration();
    }

}
