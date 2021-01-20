package org.akurskiy.parser.thread;

import org.akurskiy.parser.entity.Row;
import org.akurskiy.parser.parser.ParserManager;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@Scope( "prototype" )
public class ParseTask implements Runnable {
  private final ParserManager parserManager;
  private final Logger logger;

  private String name;

  @Autowired
  public ParseTask( Logger logger, ParserManager parserManager ) {
    this.parserManager = parserManager;
    this.logger = logger;
  }

  public String getName() {
    return name;
  }

  public void setName( String name ) {
    this.name = name;
  }

  @Override
  public void run() {
    List<Row> rows = parserManager.parse( name );
    rows.stream()
      .map( this::toJson )
      .filter( Objects::nonNull )
      .forEach( System.out::println );
  }

  private String toJson( Row row ) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      return objectMapper.writeValueAsString( row );
    } catch ( Exception e ) {
      logger.error( "Error on convert row to json: line " + row.getId() + " from " + row.getFilename(), e );
      return null;
    }
  }
}
