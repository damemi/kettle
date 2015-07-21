package hello;
 
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobDataMap;
import org.springframework.web.client.RestTemplate;
 
public class AlarmJob implements Job
{
    public void execute(JobExecutionContext context)
	throws JobExecutionException {

	JobDataMap dm = context.getJobDetail().getJobDataMap(); 
	System.out.println("Hello Quartz!");
	RestTemplate rt = new RestTemplate();
	rt.getForObject("http://localhost/on",String.class);
 
    }
 
}