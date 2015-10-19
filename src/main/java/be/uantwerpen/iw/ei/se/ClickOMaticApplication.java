package be.uantwerpen.iw.ei.se;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@SpringBootApplication
public class ClickOMaticApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(ClickOMaticApplication.class, args);
    }
}
