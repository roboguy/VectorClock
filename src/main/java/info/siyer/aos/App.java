package info.siyer.aos;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Hello world!
 *
 */
public class App 
{
	static final Logger logger = LogManager.getLogger(App.class);
	
	public static void main( String[] args )
    {
		logger.trace("IN MAIN METHOD");
		String classpath = System.getProperty("java.class.path");
		System.out.printf("Classpath : %s", classpath);
        logger.trace("EXITING..");
    }
}
