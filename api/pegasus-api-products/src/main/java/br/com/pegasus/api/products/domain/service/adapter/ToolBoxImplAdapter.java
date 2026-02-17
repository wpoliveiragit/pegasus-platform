package br.com.pegasus.api.products.domain.service.adapter;

import br.com.pegasus.api.products.domain.adapter.GlobalExceptionAdapter;
import br.com.pegasus.api.products.domain.adapter.ToolBoxAdapter;
import br.com.pegasus.api.products.domain.adapter.ProductsRepositoryAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ToolBoxImplAdapter implements ToolBoxAdapter {

  private final ProductsRepositoryAdapter productsRepository;
  private final GlobalExceptionAdapter globalException;

  @Override
  public ProductsRepositoryAdapter getProductsRepository() {
    return productsRepository;
  }

  @Override
  public GlobalExceptionAdapter getGlobalException() {
    return globalException;
  }

}
