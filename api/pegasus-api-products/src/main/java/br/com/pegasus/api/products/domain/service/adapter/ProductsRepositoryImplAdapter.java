package br.com.pegasus.api.products.domain.service.adapter;

import br.com.pegasus.api.products.domain.adapter.ProductsRepositoryAdapter;
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
@Component
@RequiredArgsConstructor
public class ProductsRepositoryImplAdapter implements ProductsRepositoryAdapter {

  private static final String CLASS_NAME = ProductsRepositoryImplAdapter.class.getSimpleName();
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
  public Optional<ProductModel> findById(ProductModel model) {
    log.info("{}::find-by-id::params: {}", CLASS_NAME, model);
    Optional<ProductModel> resp = repo.findById(model.getId()).map(ProductMapper::toModel);
    log.info("{}::find-by-id::response: {}", CLASS_NAME, resp);
    return resp;
  }

  @Override
  public Optional<ProductModel> findByName(ProductModel model) {
    log.info("{}::find-by-name::params: {}", CLASS_NAME, model);
    Optional<ProductModel> resp = repo.findByName(model.getName()).map(ProductMapper::toModel);
    log.info("{}::find-by-name::response: {}", CLASS_NAME, resp);
    return resp;
  }

  @Override
  public ProductPageModel findAll(PaginationModel model) {
    log.info("{}::find-all::params: {}", CLASS_NAME, model);
    ProductPageModel resp = PageMapper.toModel(repo.findAll(PageRequest.of(model.getPage(), model.getSize())));
    log.info("{}::find-all::response: {}", CLASS_NAME, resp);
    return resp;
  }

  @Override
  public ProductModel save(ProductModel model) {
    log.info("{}::save::params: {}", CLASS_NAME, model);
    ProductModel resp = ProductMapper.toModel(repo.save(ProductMapper.toEntity(model)));
    log.info("{}::save::response: {}", CLASS_NAME, resp);
    return resp;
  }

  @Override
  public void deleteById(ProductModel model) {
    log.info("{}::delete-by-id::params: {}", CLASS_NAME, model);
    repo.deleteById(model.getId());
    log.info("{}::delete-by-id::response: void", CLASS_NAME);
  }

}
