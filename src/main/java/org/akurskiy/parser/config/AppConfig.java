package org.akurskiy.parser.config;

import org.akurskiy.parser.parser.CsvParser;
import org.akurskiy.parser.parser.JsonParser;
import org.akurskiy.parser.parser.Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@ComponentScan( {"org.akurskiy.parser.thread", "org.akurskiy.parser.parser"} )
public class AppConfig {
  @Bean
  public ThreadPoolTaskExecutor taskExecutor() {
    ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
    pool.setCorePoolSize( 5 );
    pool.setMaxPoolSize( 10 );
    pool.setWaitForTasksToCompleteOnShutdown( true );
    return pool;
  }

  @Bean
  public Logger logger() {
    return LoggerFactory.getLogger( "application" );
  }

  @Bean( name = "json" )
  public Parser getJsonParser() {
    return new JsonParser();
  }

  @Bean( name = "csv" )
  public Parser getCsvParser() {
    return new CsvParser();
  }
}
