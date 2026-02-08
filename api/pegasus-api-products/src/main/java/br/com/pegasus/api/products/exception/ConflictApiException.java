package br.com.pegasus.api.products.exception;

public class ConflictApiException extends RuntimeException{

  public ConflictApiException(String msg){
    super(msg);
  }

  public static ConflictApiException name(String name){
    return new ConflictApiException(name);
  }
}
