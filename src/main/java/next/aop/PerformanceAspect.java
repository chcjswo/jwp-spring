package next.aop;

import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Aspect
public class PerformanceAspect {

	@Pointcut("within(next.service..*) || within(next.dao..*)")
	public void performanceCheck() {}
	
	@Around("performanceCheck()")
	public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable { 
		//MethodSignature signature = (MethodSignature) pjp.getSignature();
		StopWatch watch = new StopWatch();
		// start stopwatch 
		watch.start();
		Object retVal = pjp.proceed(); 
		// stop stopwatch
		watch.stop();
		System.out.println(watch.getTotalTimeMillis());
		System.out.println(pjp.toShortString());
		Object[] list = pjp.getArgs();
		for (Object obj : list) {
			System.out.println("param == " + obj.toString());
		}
		return retVal; 
	}
}
