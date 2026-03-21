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
import java.util.Random;
import java.util.stream.IntStream;

@Log4j2
@Component
@RequiredArgsConstructor
public class ProductsRepositoryImplAdapter implements ProductsRepositoryAdapter {

  private static class Const {
    private static final String CLASS_NAME = ProductsRepositoryImplAdapter.class.getSimpleName();

    private static final String LOG_FIND_BY_ID_PARAMS = CLASS_NAME + "::findById::params: {}";
    private static final String LOG_FIND_BY_ID_RESPONSE = CLASS_NAME + "::findById::response: {}";

    private static final String LOG_FIND_BY_NAME_PARAMS = CLASS_NAME + "::findByName::params: {}";
    private static final String LOG_FIND_BY_NAME_RESPONSE = CLASS_NAME + "::findByName::response: {}";

    private static final String LOG_FIND_ALL_PARAMS = CLASS_NAME + "::findAll::params: {}";
    private static final String LOG_FIND_ALL_RESPONSE = CLASS_NAME + "::findAll::response: {}";

    private static final String LOG_SAVE_PARAMS = CLASS_NAME + "::save::params: {}";
    private static final String LOG_SAVE_RESPONSE = CLASS_NAME + "::save::response: {}";

    private static final String LOG_DELETE_PARAMS = CLASS_NAME + "::delete::params: {}";
    private static final String LOG_DELETE_RESPONSE = CLASS_NAME + "::delete::response: VOID";
  }

  private final ProductsRepository repo;

  @PostConstruct
  public void init() {// add 20 elementos ao banco
    System.out.println("\n\n# Adicionando itens ao banco de dados");
    Random random = new Random();
    IntStream.rangeClosed(1, 20).forEach(i -> {
      var entity = ProductEntity.builder()//
          .name("Prod-" + i)//
          .price(random.nextInt(100, 10000) / 100f)//
          .quantity(random.nextInt(30))//
          .build();
      entity = repo.save(entity);
      System.out.println(i + ") " + entity);
    });
  }

  @Override
  public Optional<ProductModel> findById(ProductModel model) {
    log.info(Const.LOG_FIND_BY_ID_PARAMS, model);
    Optional<ProductModel> resp = repo.findById(model.getId()).map(ProductMapper::toModel);
    log.info(Const.LOG_FIND_BY_ID_RESPONSE, resp);
    return resp;
  }

  @Override
  public Optional<ProductModel> findByName(ProductModel model) {
    log.info(Const.LOG_FIND_BY_NAME_PARAMS, model);
    Optional<ProductModel> resp = repo.findByName(model.getName()).map(ProductMapper::toModel);
    log.info(Const.LOG_FIND_BY_NAME_RESPONSE, resp);
    return resp;
  }

  @Override
  public ProductPageModel findAll(PaginationModel model) {
    log.info(Const.LOG_FIND_ALL_PARAMS, model);
    ProductPageModel resp = PageMapper.toModel(repo.findAll(PageRequest.of(model.getPage(), model.getSize())));
    log.info(Const.LOG_FIND_ALL_RESPONSE, resp);
    return resp;
  }

  @Override
  public ProductModel save(ProductModel model) {
    log.info(Const.LOG_SAVE_PARAMS, model);
    ProductModel resp = ProductMapper.toModel(repo.save(ProductMapper.toEntity(model)));
    log.info(Const.LOG_SAVE_RESPONSE, resp);
    return resp;
  }

  @Override
  public void deleteById(ProductModel model) {
    log.info(Const.LOG_DELETE_PARAMS, model);
    repo.deleteById(model.getId());
    log.info(Const.LOG_DELETE_RESPONSE);
  }

}
