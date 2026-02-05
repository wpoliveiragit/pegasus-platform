package br.com.pegasus.api.service.a.exception;

public class ConflictApiException extends RuntimeException{

  public ConflictApiException(String msg){
    super(msg);
  }
}
