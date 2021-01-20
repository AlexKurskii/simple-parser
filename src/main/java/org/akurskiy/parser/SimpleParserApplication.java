package org.akurskiy.parser;

import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import org.akurskiy.parser.config.AppConfig;
import org.akurskiy.parser.thread.ParseTask;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Properties;

@SpringBootApplication
public class SimpleParserApplication {

  public static void main( String[] args ) {
    if ( args == null )
      return;

    SpringApplication springApplication = new SpringApplication( SimpleParserApplication.class );
    Properties properties = new Properties();
    properties.put( "logging.file.name", "application.log" );
    springApplication.setDefaultProperties( properties );

    SpringApplication.run( SimpleParserApplication.class, args );

    ApplicationContext context = new AnnotationConfigApplicationContext( AppConfig.class );
    ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) context.getBean( "taskExecutor" );
    Logger logger = (Logger) context.getBean( "logger" );

    for ( String arg : args ) {
      ParseTask parseTask = (ParseTask) context.getBean( "parseTask" );
      parseTask.setName( arg );
      taskExecutor.execute( parseTask );
    }

    for ( ; ; ) {
      int count = taskExecutor.getActiveCount();
      try {
        Thread.sleep( 1000 );
      } catch ( InterruptedException e ) {
        logger.error( "Process finished with exception. Args: " + String.join( "\n", args ), e );
      }
      if ( count == 0 ) {
        taskExecutor.shutdown();
        break;
      }
    }

    logger.debug( "Process finished" );
  }

}
