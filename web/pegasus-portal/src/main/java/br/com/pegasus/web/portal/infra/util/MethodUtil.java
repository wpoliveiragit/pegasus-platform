package br.com.pegasus.web.portal.infra.util;

import br.com.pegasus.web.portal.infra.logger.TraceLoggerAdapter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

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

  public static <R> R callWithTrace(TraceLoggerAdapter traceLog, Supplier<R> action) {
    try {
      return action.get();
    } catch (Exception ex) {
      throw new GlobalException(ex, HttpStatus.INTERNAL_SERVER_ERROR, traceLog);
    }
  }

  public static void runWithTrace(TraceLoggerAdapter traceLog, Runnable action) {
    try {
      action.run();
    } catch (Exception ex) {
      throw new GlobalException(ex, HttpStatus.INTERNAL_SERVER_ERROR, traceLog);
    }
  }
}
