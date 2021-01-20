package org.akurskiy.parser.parser;

import com.opencsv.CSVReader;
//import com.opencsv.bean.CsvBindByPosition;
//import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvException;
import org.akurskiy.parser.entity.Row;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class CsvParser extends AbstractFileParser {
  @Override
  public List<Row> parse( Path path ) throws Exception {
    return parse( path.toString(), new FileReader( path.toFile() ) );
  }

  List<Row> parse( String filename, Reader reader ) throws IOException, CsvException {
    AtomicInteger counter = new AtomicInteger( 0 );
    try ( CSVReader readerCSV = new CSVReader( reader ) ) {
      List<String[]> rows = readerCSV.readAll();
      return rows.stream()
        .map( rowData -> toRow( counter.incrementAndGet(), filename, rowData ) )
        .collect( Collectors.toList() );
    }
    /*
    List<RowCsv> rows = new CsvToBeanBuilder( reader )
      .withType( RowCsv.class )
      .build()
      .parse();
    */
  }

  private Row toRow( int indexRow, String filename, String[] rowData ) {
    if ( rowData.length != Row.REQUIRED_FIELDS_COUNT )
      return getIncompleteRow( filename, indexRow );

    List<String> problems = new ArrayList<>();
    Integer orderId = null;
    try {
      orderId = Integer.parseInt( rowData[0] );
    } catch ( Exception e ) {
      problems.add( "Can not parse orderId." );
    }
    Float amount = null;
    try {
      amount = Float.parseFloat( rowData[1] );
    } catch ( Exception e ) {
      problems.add( "Can not parse amount." );
    }

    String result = problems.isEmpty() ? Row.OK : String.join( " ", problems );

    return new Row( orderId, amount, rowData[2], rowData[3], filename, indexRow, result );
  }

  /*
  static class RowCsv {
    @CsvBindByPosition( position = 0 )
    private String id;
    @CsvBindByPosition( position = 1 )
    private String amount;
    @CsvBindByPosition( position = 2 )
    private String currency;
    @CsvBindByPosition( position = 3 )
    private String comment;
  }
  */
}
