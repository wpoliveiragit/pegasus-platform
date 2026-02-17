package br.com.pegasus.api.products.infra.util;

import br.com.pegasus.api.products.infra.exception.GlobalException;
import br.com.pegasus.api.products.domain.adapter.TraceLoggerAdapter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.http.HttpStatus;

import java.util.function.Supplier;

public final class MethodUtil {

  private static final ObjectMapper mapper = new ObjectMapper()
      .registerModule(new JavaTimeModule())
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

  public static String toJson(Object obj) {
    try {
      return mapper.writeValueAsString(obj);
    } catch (Exception ex) {
      return "{Err: }" + ex.getMessage();
    }
  }

  public static <R> R callWithTrace(TraceLoggerAdapter traceLog, Supplier<R> action) {
    try {
      return action.get();
    } catch (Exception ex) {
      throw ensureGlobalException(ex, traceLog);
    }
  }

  public static void runWithTrace(TraceLoggerAdapter traceLog, Runnable action) {
    try {
      action.run();
    } catch (Exception ex) {
      throw ensureGlobalException(ex, traceLog);
    }
  }

  private static GlobalException ensureGlobalException(Exception ex, TraceLoggerAdapter traceLog) {
    return (ex instanceof GlobalException ge) ? ge : GlobalException.internalServerError(traceLog, ex);
  }

}
