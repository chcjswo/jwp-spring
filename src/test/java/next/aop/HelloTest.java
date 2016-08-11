package next.aop;

import java.lang.reflect.Proxy;

import org.junit.Test;
import org.springframework.aop.framework.ProxyFactoryBean;

public class HelloTest {
	@Test
	public void hello() throws Exception {
		Hello hello = new HelloTarget();
		HelloUppercase proxy = new HelloUppercase(hello);
		System.out.println(proxy.sayHello("chcjswo"));
	}

	@Test
	public void hello2() throws Exception {
		Hello proxiedHello = (Hello) Proxy.newProxyInstance(
				getClass().getClassLoader(),
				new Class[] { Hello.class },
				new UppercaseHandler(new HelloTarget()));

		System.out.println(proxiedHello.sayHello("fdeffsdasdfsadf"));
	}
	
	@Test
	public void hello23() throws Exception {

		ProxyFactoryBean pfBean = new ProxyFactoryBean(); 
		pfBean.setTarget(new HelloTarget()); pfBean.addAdvice(new UppercaseAdvice());

		Hello proxiedHello = (Hello) pfBean.getObject();
		
		System.out.println(proxiedHello.sayHello("asdfasdfas dfefddef"));
	}
	
	
}
