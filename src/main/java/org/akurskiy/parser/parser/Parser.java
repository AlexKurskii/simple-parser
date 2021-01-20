package org.akurskiy.parser.parser;

import org.akurskiy.parser.entity.Row;

import java.nio.file.Path;
import java.util.List;

public interface Parser {
  List<Row> parse( Path path ) throws Exception;
}
