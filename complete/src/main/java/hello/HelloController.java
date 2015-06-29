package hello;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.io.IOException;

@RestController
public class HelloController {
    
    @RequestMapping("/on")
    public String on() {
	try {
	    Process p = Runtime.getRuntime().exec("/usr/local/bin/gpio write 4 on");
	} catch(IOException e) {
	    e.printStackTrace();
	}
        return "Turning on. <a href=\"/off\">Turn off</a>";
    }

    @RequestMapping("/off")
    public String off() {
	try {
	    Process p = Runtime.getRuntime().exec("/usr/local/bin/gpio write 4 off");
	} catch(IOException e) {
	    e.printStackTrace();
	}
        return "Turning off. <a href=\"/on\">Turn on</a>";
    }

    @RequestMapping("/mode")
    public String mode() {
	try {
	    Process p = Runtime.getRuntime().exec("/usr/local/bin/gpio mode 4 out");
	} catch(IOException e) {
	    e.printStackTrace();
	}
        return "Turning mode to out";
    }

    
}
