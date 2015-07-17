package hello;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import java.io.IOException;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application {
    
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);

	try {
	    Process p = Runtime.getRuntime().exec("/usr/local/bin/gpio mode 4 out");
	    System.out.println("[INFO] Set gpio mode to out\n");
	} catch(IOException e) {
	    e.printStackTrace();
	}
        
        System.out.println("Kettle is up and running!\n");        
	
    }

}
