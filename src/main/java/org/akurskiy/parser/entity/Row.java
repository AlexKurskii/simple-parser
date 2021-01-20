package org.akurskiy.parser.entity;

import java.util.concurrent.atomic.AtomicInteger;

public class Row {
  public static final int REQUIRED_FIELDS_COUNT = 4;
  public static final String OK = "OK";

  private static volatile AtomicInteger counter = new AtomicInteger( 0 );
  private final int id2;
  private final Integer id;
  private final Float amount;
  private final String currency;
  private final String comment;
  private final String filename;
  private final int line;
  private final String result;

  public Row( Integer orderId, Float amount, String currency, String comment, String filename, int line, String result ) {
    this.id2 = counter.getAndIncrement();
    this.id = orderId;
    this.amount = amount;
    this.currency = currency;
    this.comment = comment;
    this.filename = filename;
    this.line = line;
    this.result = result;
  }

  public Integer getId() {
    return id;
  }

  public Float getAmount() {
    return amount;
  }

  public String getCurrency() {
    return currency;
  }

  public String getComment() {
    return comment;
  }

  public String getFilename() {
    return filename;
  }

  public int getLine() {
    return line;
  }

  public String getResult() {
    return result;
  }

  public int getId2() {
    return id2;
  }
}
