package br.com.pegasus.web.portal.infra.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Log4j2
@Component
public final class MethodUtil {

  private static final ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
  private static final ObjectMapper toPrettyJsonMapper = new ObjectMapper();

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

  public static String toPrettyJson(Object obj) {
    try {
      return toPrettyJsonMapper
          .writerWithDefaultPrettyPrinter()
          .writeValueAsString(obj);
    } catch (Exception e) {
      return obj != null ? obj.toString() : null;
    }
  }


}
