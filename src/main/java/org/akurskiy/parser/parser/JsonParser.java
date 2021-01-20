package org.akurskiy.parser.parser;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.akurskiy.parser.entity.Row;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JsonParser extends AbstractFileParser {

  @Override
  public List<Row> parse( Path path ) throws Exception {
    return parse( path.toString(), getLines( path ) );
  }

  List<Row> parse( String filename, Stream<String> lines ) {
    AtomicInteger counter = new AtomicInteger( 0 );
    return lines.map( line -> toRow( counter.incrementAndGet(), filename, line ) ).collect( Collectors.toList() );
  }

  private Row toRow( int indexRow, String filename, String json ) {
    try {
      ObjectMapper mapper = new ObjectMapper()
        .configure( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true );
      RowJson row = mapper.readValue( json, RowJson.class );
      if ( row.orderId == null ||
        row.amount == null ||
        row.currency == null ||
        row.comment == null ) {
        return getIncompleteRow( filename, indexRow );
      }
      return new Row( row.orderId, row.amount, row.currency, row.comment, filename, indexRow, Row.OK );
    } catch ( Exception e ) {
      String result = e.getMessage();
      if ( result == null || Row.OK.equals( result ) )
        result = "Exception while parse json";
      return new Row( null, null, null, null, filename, indexRow, result );
    }
  }

  private static class RowJson {
    public Integer orderId;
    public Float amount;
    public String currency;
    public String comment;
  }
}
