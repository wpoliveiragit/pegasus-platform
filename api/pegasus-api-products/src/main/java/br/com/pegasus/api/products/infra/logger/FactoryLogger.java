package br.com.pegasus.api.products.infra.logger;

import org.springframework.stereotype.Component;

@Component
public class FactoryLogger {

  public TraceLogger create() {
    return new TraceLogger();
  }

}
