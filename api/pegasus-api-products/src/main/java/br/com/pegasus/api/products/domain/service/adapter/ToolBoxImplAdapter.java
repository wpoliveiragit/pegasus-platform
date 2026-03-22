package br.com.pegasus.api.products.domain.service.adapter;

import br.com.pegasus.api.products.domain.adapter.AppLoggerAdapter;
import br.com.pegasus.api.products.domain.adapter.ProductsRepositoryAdapter;
import br.com.pegasus.api.products.domain.adapter.ToolBoxAdapter;
import br.com.pegasus.api.products.infra.logger.AppLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ToolBoxImplAdapter implements ToolBoxAdapter {

  private final ProductsRepositoryAdapter productsRepository;

  @Override
  public ProductsRepositoryAdapter getProductsRepository() {
    return productsRepository;
  }

  @Override
  public AppLoggerAdapter getAppLoggerAdapter(Class<?> clazz) {
    final AppLogger log = new AppLogger(clazz);
    return new AppLoggerAdapter() {
      @Override
      public void info(String message, Object... params) {
        log.info(message, params);
      }

      @Override
      public void warn(String message, Object... params) {
        log.warn(message, params);
      }

      @Override
      public void error(String message, Object... params) {
        log.error(message, params);
      }

      @Override
      public void infoPattern(String methodName, String patternParams, Object... args) {
        log.infoPattern(methodName, patternParams, args);
      }
    };
  }

}
