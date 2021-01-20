package org.akurskiy.parser.parser;

import org.akurskiy.parser.entity.Row;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class JsonParserTest {
  JsonParser parser = new JsonParser();

  @Test
  public void testEmptyRow() {
    String fileName = "someFileName";
    String json = "";
    List<Row> rows = parser.parse( fileName, Stream.of(json) );
    assertEquals( 1, rows.size() );

    Row row = rows.get(0);
    assertEquals( fileName, row.getFilename() );
    assertEquals( 1, row.getLine() );
    assertNotEquals( Row.OK, row.getResult() );
  }

  @Test
  public void testSimpleRow() {
    String fileName = "someFileName";
    String json = "{\"orderId\":15,\"amount\":22.0,\"currency\":\"USD\",\"comment\":\"test1\"}";
    List<Row> rows = parser.parse( fileName, Stream.of(json) );
    assertEquals( 1, rows.size() );

    Row row = rows.get(0);
    assertEquals( fileName, row.getFilename() );
    assertEquals( 1, row.getLine() );
    assertEquals( Row.OK, row.getResult() );
    assertEquals( new Integer(15), row.getId() );
    assertEquals( new Float(22.0), row.getAmount() );
    assertEquals( "USD", row.getCurrency() );
    assertEquals( "test1", row.getComment() );
  }

  @Test
  public void testSimpleRowWithoutComment() {
    String fileName = "someFileName";
    String json = "{\"orderId\":15,\"amount\":22.0,\"currency\":\"USD\"}";
    List<Row> rows = parser.parse( fileName, Stream.of(json) );
    assertEquals( 1, rows.size() );

    Row row = rows.get(0);
    assertEquals( fileName, row.getFilename() );
    assertEquals( 1, row.getLine() );
    assertNotEquals( Row.OK, row.getResult() );
  }

  @Test
  public void testMultipleRows() {
    String fileName = "someFileName";
    String json = "{\"orderId\":10,\"amount\":23.0,\"currency\":\"EUR\",\"comment\":\"test1\"}";
    String json2 = "{\"orderId\":11,\"amount\":22.0,\"currency\":\"USD\",\"comment\":\"test2\"}";
    List<Row> rows = parser.parse( fileName, Stream.of(json, json2) );
    assertEquals( 2, rows.size() );

    Row row1 = rows.get(0);
    assertEquals( fileName, row1.getFilename() );
    assertEquals( 1, row1.getLine() );
    assertEquals( Row.OK, row1.getResult() );
    assertEquals( new Integer(10), row1.getId() );
    assertEquals( new Float(23.0), row1.getAmount() );
    assertEquals( "EUR", row1.getCurrency() );
    assertEquals( "test1", row1.getComment() );

    Row row2 = rows.get(1);
    assertEquals( fileName, row2.getFilename() );
    assertEquals( 2, row2.getLine() );
    assertEquals( Row.OK, row2.getResult() );
    assertEquals( new Integer(11), row2.getId() );
    assertEquals( new Float(22.0), row2.getAmount() );
    assertEquals( "USD", row2.getCurrency() );
    assertEquals( "test2", row2.getComment() );
  }

//  @Test
  public void testMultipleJsonAtSingleRow() {
    String fileName = "someFileName";
    String json = "{\"orderId\":1,\"amount\":22.0,\"currency\":\"USD\",\"comment\":\"test1\"}{\"orderId\":1,\"amount\":22.0,\"currency\":\"USD\",\"comment\":\"test1\"}";
    List<Row> rows = parser.parse( fileName, Stream.of(json) );
    assertEquals( 1, rows.size() );

    Row row = rows.get(0);
    assertEquals( fileName, row.getFilename() );
    assertEquals( 1, row.getLine() );
    assertNotEquals( Row.OK, row.getResult() );
  }
}
