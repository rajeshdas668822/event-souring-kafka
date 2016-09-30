package demo.orderflow;

import org.junit.BeforeClass;

import java.util.logging.Handler;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class AbstractTest {
	
	@BeforeClass
	  public static void routeLoggingToSlf4j() {
	   // LogUtil.readJavaUtilLoggingConfigFromClasspath();
	    Logger rootLogger =
	        LogManager.getLogManager().getLogger("");
	    Handler[] handlers = rootLogger.getHandlers();
	    for (int i = 0; i < handlers.length; i++) {
	      rootLogger.removeHandler(handlers[i]);
	    }
	   // SLF4JBridgeHandler.install();
	  }

}
