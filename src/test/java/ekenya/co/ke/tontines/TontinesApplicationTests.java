package ekenya.co.ke.tontines;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;


public class TontinesApplicationTests {

    @Test
    public void contextLoads() {

        BCryptPasswordEncoder b = new BCryptPasswordEncoder();
        String pas = b.encode("fep@2019");

        System.out.println(pas);

        if (b.matches("cupcake",pas)){
            System.out.println("found...");
        }
    }

}
