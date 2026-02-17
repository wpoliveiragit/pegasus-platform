package br.com.pegasus.api.products.domain.config;

import br.com.pegasus.api.products.domain.adapter.GlobalExceptionAdapter;
import br.com.pegasus.api.products.domain.adapter.ToolBoxAdapter;
import br.com.pegasus.api.products.domain.adapter.TraceLoggerAdapter;
import br.com.pegasus.api.products.infra.exception.GlobalException;
import br.com.pegasus.api.products.domain.adapter.ProductsRepositoryAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ToolBoxAdapterConfig implements ToolBoxAdapter {

  private final ProductsRepositoryAdapter repo;

  @Override
  public ProductsRepositoryAdapter getProductsRepository() {
    return repo;
  }

  @Override
  public GlobalExceptionAdapter getGlobalException() {
    return createGlobalException();
  }

  private GlobalExceptionAdapter createGlobalException() {
    return new GlobalExceptionAdapter() {
      @Override
      public RuntimeException conflictName(TraceLoggerAdapter traceLog, String name) {
        return new GlobalException("Existing name '" + name + "'", HttpStatus.CONFLICT, traceLog);
      }

      @Override
      public RuntimeException notFoundId(TraceLoggerAdapter traceLog, Long id) {
        return new GlobalException("Product Not Found by id=" + id, HttpStatus.NOT_FOUND, traceLog);
      }
    };
  }

}
