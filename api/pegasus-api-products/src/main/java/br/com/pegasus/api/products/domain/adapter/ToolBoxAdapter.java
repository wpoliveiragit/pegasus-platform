package br.com.pegasus.api.products.domain.adapter;

public interface ToolBoxAdapter {
  ProductsRepositoryAdapter getProductsRepository();
  GlobalExceptionAdapter getGlobalException();
}
