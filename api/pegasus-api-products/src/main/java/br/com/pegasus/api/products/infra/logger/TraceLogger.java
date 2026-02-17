package br.com.pegasus.api.products.infra.logger;

import br.com.pegasus.api.products.domain.adapter.TraceLoggerAdapter;
import br.com.pegasus.api.products.domain.model.TraceModel;
import br.com.pegasus.api.products.infra.util.MethodUtil;
import lombok.extern.log4j.Log4j2;
import org.slf4j.helpers.MessageFormatter;

import java.util.ArrayList;
import java.util.List;

@Log4j2
public class TraceLogger implements TraceLoggerAdapter {

  private int totalTracking = 0;
  private final List<TraceModel> traces = new ArrayList<>();

  public void addTrace(String message, Object... args) {
    traces.add(
        new TraceModel(
            ++totalTracking,
            MessageFormatter.arrayFormat(message, args).getMessage()
        )
    );
  }

  @Override
  public void logInfo(Object obj) {
    log.info((obj));
  }

  @Override
  public String toString() {
    return MethodUtil.toJson(traces);
  }

}
