package hello;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import java.io.IOException;
import hello.AlarmJob;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
//import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.SimpleTrigger;
//import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;
import static org.quartz.DateBuilder.*;
import java.util.*;


@RestController
public class HelloController {
    
    @RequestMapping(value = "/setAlarm/{hour}/{minute}/{ampm}")
	@ResponseBody
	public String setAlarm(@PathVariable("hour") String hour,
			       @PathVariable("minute") String minute,
			       @PathVariable("ampm") String ampm) {

	try {
	    long startTime = System.currentTimeMillis() + 10000L;
	    Date myStart = new Date(startTime);
	    JobDetail job = JobBuilder.newJob(AlarmJob.class)
		.withIdentity("alarmJob")
		.build();
	
	    SimpleTrigger trigger = (SimpleTrigger) newTrigger() 
		.withIdentity("trigger1")
		.startAt(myStart)
		.forJob("alarmJob")
		.build();

	    SchedulerFactory schFactory = new StdSchedulerFactory();
	    Scheduler sch = schFactory.getScheduler();
	    sch.start();
	    sch.scheduleJob(job, trigger);
	} catch (SchedulerException e) {
	    e.printStackTrace();
	}
	return "True";
    }

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
