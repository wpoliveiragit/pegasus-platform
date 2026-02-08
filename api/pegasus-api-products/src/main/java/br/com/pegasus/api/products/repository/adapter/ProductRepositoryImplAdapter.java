package br.com.pegasus.api.products.repository.adapter;

import br.com.pegasus.api.products.mapper.PageMapper;
import br.com.pegasus.api.products.mapper.ProductMapper;
import br.com.pegasus.api.products.model.PaginationModel;
import br.com.pegasus.api.products.model.ProductModel;
import br.com.pegasus.api.products.model.ProductPageModel;
import br.com.pegasus.api.products.repository.ProductsRepository;
import br.com.pegasus.api.products.repository.entity.ProductEntity;
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
public class ProductRepositoryImplAdapter implements ProductRepositoryAdapter {

  private final ProductsRepository repo;

  @PostConstruct
  public void init() {
    // add 20 elementos ao banco
    log.info("# Adicionando itens ao banco de dados");
    System.out.println("\nDatabase: itens adicionados");
    IntStream.rangeClosed(1, 20)//
        .forEach(i -> System.out.println(i + ")" + repo.save(new ProductEntity("Prod-" + i, 35f, 5))));
  }

  @Override
  public Optional<ProductModel> findById(ProductModel inModel) {
    Optional<ProductModel> outModelOpt = repo.findById(inModel.getId()).map(ProductMapper::toModel);
    log.info("Repository::find-by-id: {}", outModelOpt.isPresent()//
        ? outModelOpt.get() : "Produto não encontrado");
    return outModelOpt;
  }

  @Override
  public Optional<ProductModel> findByName(ProductModel inModel) {
    Optional<ProductModel> outModelOpt = repo.findByName(inModel.getName()).map(ProductMapper::toModel);
    log.info("Repository::find-by-name: {}", outModelOpt.isPresent()//
        ? outModelOpt.get() : "Produto não encontrado");
    return outModelOpt;
  }

  @Override
  public ProductPageModel findAll(PaginationModel inModel) {
    ProductPageModel outModel = PageMapper.toModel(repo.findAll(PageRequest.of(inModel.page(), inModel.size())));
    log.info("repository::findAll: {}", outModel.getPagination());
    return outModel;
  }

  @Override
  public ProductModel save(ProductModel inModel) {
    ProductModel outModel = ProductMapper.toModel(repo.save(ProductMapper.toEntity(inModel)));
    log.info("repository::save: {}", outModel);
    return outModel;
  }

  @Override
  public void deleteById(ProductModel inModel) {
    repo.deleteById(inModel.getId());
    log.info("repository::delete: item id({}) deletado", inModel);
  }

}
