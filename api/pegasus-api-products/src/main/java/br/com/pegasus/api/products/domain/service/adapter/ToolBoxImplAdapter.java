package br.com.pegasus.api.products.domain.service.adapter;

import br.com.pegasus.api.products.domain.adapter.ProductsRepositoryAdapter;
import br.com.pegasus.api.products.domain.adapter.ToolBoxAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class ToolBoxImplAdapter implements ToolBoxAdapter {

  private final ProductsRepositoryAdapter productsRepository;

  @Override
  public ProductsRepositoryAdapter getProductsRepository() {
    return productsRepository;
  }
}
