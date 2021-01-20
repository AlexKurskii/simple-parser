package org.akurskiy.parser.parser;

import org.akurskiy.parser.config.AppConfig;
import org.akurskiy.parser.entity.Row;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class ParserManagerTest {

  @Autowired
  private ParserManager parserManager;
  private Path workingDir;

  @BeforeEach
  public void init() {
    this.workingDir = Paths.get( "src/test/resources" );
  }

  @Test
  void testParseJsonFile() throws Exception {
    Path path = this.workingDir.resolve( "test2.json" );
    List<Row> rows = parserManager.getParser( "json" ).parse( path );
    assertEquals( 7, rows.size() );
  }

  @Test
  void testParseCsvFile() throws Exception {
    Path path = this.workingDir.resolve( "test1.csv" );
    List<Row> rows = parserManager.getParser( "csv" ).parse( path );
    assertEquals( 6, rows.size() );
  }
}
