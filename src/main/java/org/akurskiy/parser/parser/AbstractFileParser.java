package org.akurskiy.parser.parser;

import org.akurskiy.parser.entity.Row;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

abstract public class AbstractFileParser implements Parser {
  protected Row getIncompleteRow( String filename, int indexRow ) {
    return new Row( null, null, null, null, filename, indexRow, "fields count must be " + Row.REQUIRED_FIELDS_COUNT );
  }

  protected Stream<String> getLines( Path path ) throws IOException {
    return Files.lines( path );
  }
}
