package br.com.pegasus.api.products.domain.core;

import br.com.pegasus.api.products.domain.adapter.GlobalExceptionAdapter;
import br.com.pegasus.api.products.domain.adapter.ToolBoxAdapter;
import br.com.pegasus.api.products.domain.adapter.TraceLoggerAdapter;
import br.com.pegasus.api.products.domain.model.PaginationModel;
import br.com.pegasus.api.products.domain.model.ProductModel;
import br.com.pegasus.api.products.domain.model.ProductPageModel;
import br.com.pegasus.api.products.domain.port.ProductsServicePort;
import br.com.pegasus.api.products.domain.adapter.ProductsRepositoryAdapter;
import org.springframework.transaction.annotation.Transactional;

public class ProductsServiceCore implements ProductsServicePort {

  private static final String NAME_CLASS = ProductsServiceCore.class.getSimpleName();
  private final ProductsRepositoryAdapter repo;
  private final GlobalExceptionAdapter exTool;

  public ProductsServiceCore(ToolBoxAdapter toolBox) {
    this.repo = toolBox.getProductsRepository();
    this.exTool = toolBox.getGlobalException();
  }

  @Transactional(readOnly = true) //dependencia, spring data (não permite nenhum tipo de update no banco)
  @Override
  public ProductModel getOne(TraceLoggerAdapter traceLog, ProductModel model) {
    traceLog.addTrace("{}::getOne::in: {}", NAME_CLASS, model);
    ProductModel resp = repo.findById(traceLog, model).orElseThrow(() -> exTool.notFoundId(traceLog, model.getId()));
    traceLog.addTrace("{}::getOne::out: {}", NAME_CLASS, resp);
    return resp;
  }

  @Transactional(readOnly = true)
  @Override
  public ProductPageModel getAll(TraceLoggerAdapter traceLog, PaginationModel model) {
    traceLog.addTrace("{}::getAll::in: {}", NAME_CLASS, model);
    ProductPageModel resp = repo.findAll(traceLog, model);
    traceLog.addTrace("{}::getAll::out: {}", NAME_CLASS, resp);
    return resp;
  }

  @Override
  public ProductModel create(TraceLoggerAdapter traceLog, ProductModel model) {
    traceLog.addTrace("{}::create::in: {}", NAME_CLASS, model);
    checkName(traceLog, model);
    ProductModel resp = repo.save(traceLog, model);
    traceLog.addTrace("{}::create::out: {}", NAME_CLASS, resp);
    return resp;
  }

  @Override
  public ProductModel update(TraceLoggerAdapter traceLog, ProductModel model) {
    traceLog.addTrace("{}::update::in: {}", NAME_CLASS, model);
    ProductModel upModel = getOne(traceLog, model);
    if (!upModel.getName().equals(model.getName())) {
      checkName(traceLog, model);
    }
    ProductModel resp = repo.save(traceLog, model);
    traceLog.addTrace("{}::update::out: {}", NAME_CLASS, resp);
    return resp;
  }

  @Override
  public void delete(TraceLoggerAdapter traceLog, ProductModel model) {
    traceLog.addTrace("{}::delete::in: {}", NAME_CLASS, model);
    repo.deleteById(traceLog, model);
    traceLog.addTrace("{}::delete::out: void", NAME_CLASS);
  }

  /**
   * Verifica se o nome existe no banco de dados, caso propsitivo uma exception de conflito será disparada.
   *
   * @param model o modelo com o nome a ser verificado
   */
  private void checkName(TraceLoggerAdapter traceLog, ProductModel model) {
    traceLog.addTrace("{}::checkName::in: {}", NAME_CLASS, model);
    repo.findByName(traceLog, model).ifPresent(e -> {
      throw exTool.conflictName(traceLog, model.getName());
    });
    traceLog.addTrace("{}::checkName::out: void", NAME_CLASS);
  }

}
