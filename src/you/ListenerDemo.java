package you;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ListenerDemo implements ServletContextListener{

	private SocketServerDemo s;
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("监听器关闭server");
		s.interrupt();
		s = null;
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("监听器启动server");
		if (s == null) {
			s = new SocketServerDemo();
			s.start();
		}
	}

}
