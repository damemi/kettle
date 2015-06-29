package hello;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.io.IOException;

@RestController
public class HelloController {
    
    @RequestMapping("/greeting")
    public String index() {
	try {
	    Process p = Runtime.getRuntime().exec(new String[]{"/usr/local/bin/gpio write 4 on"});
	} catch(IOException e) {
	    e.printStackTrace();
	}
        return "Greetings from Spring Boot!";
    }
    
}
