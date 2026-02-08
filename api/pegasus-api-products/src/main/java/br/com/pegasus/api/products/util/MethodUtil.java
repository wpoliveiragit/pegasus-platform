package br.com.pegasus.api.products.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public final class MethodUtil {
  private static final ObjectMapper mapper = new ObjectMapper();

  public static String toJson(Object obj) {
    try {
      return mapper.writeValueAsString(obj);
    } catch (Exception ex) {
      return "{Err: }" + ex.getMessage();
    }
  }
}
