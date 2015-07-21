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
import org.quartz.JobKey;
import org.quartz.JobDataMap;
//import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.SimpleTrigger;
//import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;
import static org.quartz.DateBuilder.*;
import java.util.*;
import java.text.DateFormat;


@RestController
public class HelloController {

    @RequestMapping(value = "/checkAlarm")
	@ResponseBody
	public String checkAlarm() {
	
	try {
	    SchedulerFactory schFactory = new StdSchedulerFactory();
	    Scheduler sch = schFactory.getScheduler();

	    if(sch.checkExists(new JobKey("alarmJob"))) {
		JobDataMap jd = sch.getJobDetail(new JobKey("alarmJob")).getJobDataMap();
		int hour = jd.getIntValue("hour");
		int minute = jd.getIntValue("minute");
		String ampm = jd.getString("ampm");
		return "{\"response\":\"True\",\"hour\":\""+hour+"\",\"minute\":"+minute+",\"ampm\":\""+ampm+"\"}";
	    }
	} catch (SchedulerException e) {
	    e.printStackTrace();
	}

	return "{\"response\":\"False\"}";

    }

    @RequestMapping(value = "/stopAlarm")
	@ResponseBody
	public String stopAlarm() {
	try {
	    SchedulerFactory schFactory = new StdSchedulerFactory();
	    Scheduler sch = schFactory.getScheduler();
	    sch.start();
	    sch.clear();
	} catch (SchedulerException e) {
	    e.printStackTrace();
	}
	return "{\"response\":\"True\"}";
    }
    
    @RequestMapping(value = "/setAlarm/{hour}/{minute}/{ampm}")
	@ResponseBody
	public String setAlarm(@PathVariable("hour") Integer hour,
			       @PathVariable("minute") Integer minute,
			       @PathVariable("ampm") String ampm) {

	if(ampm.equals("PM")) {
	    hour = hour + 12;
	}

	Calendar now = Calendar.getInstance();
	Calendar alarmTime = Calendar.getInstance();
	alarmTime.set(alarmTime.HOUR_OF_DAY, hour);
	alarmTime.set(alarmTime.MINUTE, minute);
	
	if(now.after(alarmTime)) {
	    alarmTime.add(alarmTime.DATE, 1);
	}
	
	try {
	    SchedulerFactory schFactory = new StdSchedulerFactory();
	    Scheduler sch = schFactory.getScheduler();
	    sch.start();
	    sch.clear();

	    long startTime = System.currentTimeMillis() + 10000L;
	    Date myStart = alarmTime.getTime();
	    JobDetail job = JobBuilder.newJob(AlarmJob.class)
		.withIdentity("alarmJob")
		.usingJobData("hour", hour)
		.usingJobData("minute", minute)
		.usingJobData("ampm", ampm)
		.build();
	
	    SimpleTrigger trigger = (SimpleTrigger) newTrigger() 
		.withIdentity("trigger1")
		.startAt(myStart)
		.forJob("alarmJob")
		.build();

	    sch.scheduleJob(job, trigger);
	} catch (SchedulerException e) {
	    e.printStackTrace();
	}
	return "{\"response\":\"True\"}";
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
