package br.com.pegasus.api.service.a.exception;

public class NotFountApiException extends RuntimeException{

  public NotFountApiException(String msg){
    super(msg);
  }

  public static NotFountApiException byId(Long id){
    return new NotFountApiException("Product Not Found. id=" + id);
  }

}
