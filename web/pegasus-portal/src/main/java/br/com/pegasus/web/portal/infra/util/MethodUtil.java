package br.com.pegasus.web.portal.infra.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public final class MethodUtil {

  private static final ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();

  public static String toJson(Object obj) {
    try {
      return mapper.writeValueAsString(obj);
    } catch (Exception ex) {
      return "{Err: }" + ex.getMessage();
    }
  }

  public static <T> T jsonToObject(String json, Class<T> returnType) {
    try {
      return mapper.readValue(json, returnType);
    } catch (Exception ex) {
      log.error(ex);
      return null;
    }
  }

}
