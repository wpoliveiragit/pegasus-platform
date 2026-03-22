package br.com.pegasus.api.products.domain.service.adapter;

import br.com.pegasus.api.products.domain.adapter.ProductsRepositoryAdapter;
import br.com.pegasus.api.products.domain.model.PaginationModel;
import br.com.pegasus.api.products.domain.model.ProductModel;
import br.com.pegasus.api.products.domain.model.ProductPageModel;
import br.com.pegasus.api.products.infra.logger.AppLogger;
import br.com.pegasus.api.products.infra.mapper.PageMapper;
import br.com.pegasus.api.products.infra.mapper.ProductMapper;
import br.com.pegasus.api.products.infra.repository.ProductsRepository;
import br.com.pegasus.api.products.infra.repository.entity.ProductEntity;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class ProductsRepositoryImplAdapter implements ProductsRepositoryAdapter {

  private final AppLogger log = new AppLogger(ProductsRepositoryImplAdapter.class);

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
    log.infoPattern("findById","params: {}", model);
    Optional<ProductModel> resp = repo.findById(model.getId()).map(ProductMapper::toModel);
    log.infoPattern("findById","response: {}", resp);
    return resp;
  }

  @Override
  public Optional<ProductModel> findByName(ProductModel model) {
    log.infoPattern("findByName","params: {}", model);
    Optional<ProductModel> resp = repo.findByName(model.getName()).map(ProductMapper::toModel);
    log.infoPattern("findByName","response: {}", resp);
    return resp;
  }

  @Override
  public ProductPageModel findAll(PaginationModel model) {
    log.infoPattern("findAll","params: {}", model);
    ProductPageModel resp = PageMapper.toModel(repo.findAll(PageRequest.of(model.getPage(), model.getSize())));
    log.infoPattern("findAll","response: {}", resp);
    return resp;
  }

  @Override
  public ProductModel save(ProductModel model) {
    log.infoPattern("save","params: {}", model);
    ProductModel resp = ProductMapper.toModel(repo.save(ProductMapper.toEntity(model)));
    log.infoPattern("save","response: {}", resp);
    return resp;
  }

  @Override
  public void deleteById(ProductModel model) {
    log.infoPattern("deleteById","params: {}", model);
    repo.deleteById(model.getId());
    log.infoPattern("deleteById","response: VOID");
  }

}
