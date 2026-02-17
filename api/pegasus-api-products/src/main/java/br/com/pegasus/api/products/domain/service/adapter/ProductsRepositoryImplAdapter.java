package br.com.pegasus.api.products.domain.service.adapter;

import br.com.pegasus.api.products.domain.adapter.ProductsRepositoryAdapter;
import br.com.pegasus.api.products.domain.adapter.TraceLoggerAdapter;
import br.com.pegasus.api.products.domain.model.PaginationModel;
import br.com.pegasus.api.products.domain.model.ProductModel;
import br.com.pegasus.api.products.domain.model.ProductPageModel;
import br.com.pegasus.api.products.infra.mapper.PageMapper;
import br.com.pegasus.api.products.infra.mapper.ProductMapper;
import br.com.pegasus.api.products.infra.repository.ProductsRepository;
import br.com.pegasus.api.products.infra.repository.entity.ProductEntity;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.IntStream;

@Log4j2
@RequiredArgsConstructor
@Component
public class ProductsRepositoryImplAdapter implements ProductsRepositoryAdapter {

  private static final String NAME_CLASS = ProductsRepositoryImplAdapter.class.getSimpleName();
  private final ProductsRepository repo;

  @PostConstruct
  public void init() {
    // add 20 elementos ao banco
    System.out.println("\n# Adicionando itens ao banco de dados");
    System.out.println("Database: itens adicionados");
    IntStream.rangeClosed(1, 20)//
        .forEach(i -> {
          ProductEntity entity = new ProductEntity();
          entity.setName("Prod-" + i);
          entity.setPrice(33.34F);
          entity.setQuantity(5);
          repo.save(entity);
          System.out.println(i + ") " + entity);
        });
  }

  @Override
  public Optional<ProductModel> findById(TraceLoggerAdapter traceLog, ProductModel model) {
    traceLog.addTrace("{}::find-by-id::in: {}", NAME_CLASS, model);
    Optional<ProductModel> resp = repo.findById(model.getId()).map(ProductMapper::toModel);
    traceLog.addTrace("{}::find-by-id::out: {}", NAME_CLASS, resp);
    return resp;
  }

  @Override
  public Optional<ProductModel> findByName(TraceLoggerAdapter traceLog, ProductModel model) {
    traceLog.addTrace("{}::find-by-name::in: {}", NAME_CLASS, model);
    Optional<ProductModel> resp = repo.findByName(model.getName()).map(ProductMapper::toModel);
    traceLog.addTrace("{}::find-by-name::out: {}", NAME_CLASS, resp);
    return resp;
  }

  @Override
  public ProductPageModel findAll(TraceLoggerAdapter traceLog, PaginationModel model) {
    traceLog.addTrace("{}::find-all::in: {}", NAME_CLASS, model);
    ProductPageModel resp = PageMapper.toModel(repo.findAll(PageRequest.of(model.getPage(), model.getSize())));
    traceLog.addTrace("{}::find-all::out: {}", NAME_CLASS, resp);
    return resp;
  }

  @Override
  public ProductModel save(TraceLoggerAdapter traceLog, ProductModel model) {
    traceLog.addTrace("{}::save::in: {}", NAME_CLASS, model);
    ProductModel resp = ProductMapper.toModel(repo.save(ProductMapper.toEntity(model)));
    traceLog.addTrace("{}::save::out: {}", NAME_CLASS, resp);
    return resp;
  }

  @Override
  public void deleteById(TraceLoggerAdapter traceLog, ProductModel model) {
    traceLog.addTrace("{}::delete-by-id::in: {}", NAME_CLASS, model);
    repo.deleteById(model.getId());
    traceLog.addTrace("{}::delete-by-id::out: void", NAME_CLASS);
  }

}
