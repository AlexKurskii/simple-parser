package org.akurskiy.parser.parser;

import org.akurskiy.parser.entity.Row;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

@Component
public class ParserManager {
  private final ApplicationContext context;
  private final Logger logger;

  @Autowired
  public ParserManager( ApplicationContext context, Logger logger ) {
    this.context = context;
    this.logger = logger;
  }

  public Parser getParser(String type) {
    return context.getBean( type, Parser.class );
  }

  public List<Row> parse( String pathToFile ) {
    List<Row> defaultValue = Collections.emptyList();
    try {
      Path path = Paths.get( pathToFile );

      if ( !Files.exists( path ) ) {
        logger.info( "Can not find file " + pathToFile );
        return defaultValue;
      }

      int indexDot = pathToFile.lastIndexOf( '.' );
      if ( indexDot < 0 ) {
        logger.info( "Can not get extension of file " + pathToFile );
        return defaultValue;
      }

      String extension = pathToFile.substring( indexDot + 1 );
      return getParser( extension ).parse( path );

    } catch ( Exception e ) {
      logger.error( "Error while process " + pathToFile, e );
    }
    return defaultValue;
  }
}
