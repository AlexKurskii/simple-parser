package org.akurskiy.parser;

import org.akurskiy.parser.config.AppConfig;
import org.akurskiy.parser.thread.ParseTask;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

@SpringBootTest
class SimpleParserApplicationTests {

  @Test
  void testBeanTaskExecutor() {
    ApplicationContext context = new AnnotationConfigApplicationContext( AppConfig.class );
    ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) context.getBean( "taskExecutor" );
    assertNotNull( taskExecutor );
  }

  @Test
  void testBeanParseTask() {
    ApplicationContext context = new AnnotationConfigApplicationContext( AppConfig.class );
    ParseTask parseTask1 = (ParseTask) context.getBean( "parseTask" );
    ParseTask parseTask2 = (ParseTask) context.getBean( "parseTask" );
    parseTask1.setName( "task1" );
    parseTask2.setName( "task2" );
    assertEquals( "task1", parseTask1.getName() );
    assertEquals( "task2", parseTask2.getName() );
  }

}
