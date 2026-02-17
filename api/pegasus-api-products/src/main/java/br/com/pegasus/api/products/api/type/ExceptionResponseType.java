package br.com.pegasus.api.products.api.type;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionResponseType {
  private int code; // http-status::value
  private String message; // http-status::reason-phrase
  private String detail; // Detalhes do problema
}
