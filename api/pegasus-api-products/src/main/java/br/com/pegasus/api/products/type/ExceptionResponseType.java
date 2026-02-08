package br.com.pegasus.api.products.type;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ExceptionResponseType {
  private final int code; // http-status::value
  private final String message; // http-status::reason-phrase
  private final String detail; // Detalhes do problema
}
