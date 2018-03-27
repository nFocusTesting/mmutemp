package org.mmu.g4sm.qa.at.selenium.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang3.text.WordUtils;
import org.testng.Reporter;

public class LoggingFactory extends ConfigurationFactory {

	public static Logger logger = null;
	
	
	public LoggingFactory() {
		LoggingFactory.setLogger( LoggerFactory.getLogger( this.getClass() ) );
	}
	
	
	private static void setLogger( Logger log ) {
        logger = log;
    }
	
	public static void info( String info ) {
        logger.info( info );
        Reporter.log(info);
    }

    public static void error( String info ) {
        logger.error( info );
    }

    public static void error(Exception exception) {
    	logger.error( "-----------------------------------------------------------------------" );
    	logger.error( "|       cause     |  " + exception.getCause() );
    	logger.error( "|       message   |  " + WordUtils.wrap( exception.getMessage(), 70 ) );
    	logger.error( "-----------------------------------------------------------------------" );
        logger.error( "ATTENTION ! Below are the lines of code where the test fails" );
        logger.error( "------------------------------------------------------------------------" );
        StackTraceElement[] strackTraceArray = exception.getStackTrace();
        for (StackTraceElement traceLine : strackTraceArray) {
        	logger.error(traceLine.toString());
        }
        logger.error( "------------------------------------------------------------------------" );
    }
}
