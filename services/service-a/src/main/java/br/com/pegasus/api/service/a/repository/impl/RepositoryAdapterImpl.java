package br.com.pegasus.api.service.a.repository.impl;

import br.com.pegasus.api.service.a.exception.ConflictApiException;
import br.com.pegasus.api.service.a.model.PaginationModel;
import br.com.pegasus.api.service.a.model.ProductModel;
import br.com.pegasus.api.service.a.repository.RepositoryAdapter;
import br.com.pegasus.api.service.a.repository.entity.PaginationResponseEntity;
import br.com.pegasus.api.service.a.repository.entity.ProductEntity;
import br.com.pegasus.api.service.a.repository.entity.ProductPageEntity;
import br.com.pegasus.api.service.a.type.ProductCreateRequestType;
import org.springframework.stereotype.Component;
import br.com.pegasus.api.service.a.model.ProductPageModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

@Component
public class RepositoryAdapterImpl implements RepositoryAdapter {

  private final AtomicLong idGen;
  private final List<ProductEntity> products;

  public RepositoryAdapterImpl() {
    idGen = new AtomicLong(1);
    products = new ArrayList<>(
        IntStream.rangeClosed(1, 20)
            .mapToObj(i -> new ProductEntity(idGen.getAndIncrement(), "Product" + i, 3500f, 10))
            .toList()
    );
  }

  @Override
  public Optional<ProductModel> findById(ProductModel inModel) {
    return products.stream()
        .filter(p -> p.getId().equals(inModel.getId()))
        .findFirst().map(this::toModel);
  }

  @Override
  public ProductPageModel findAll(PaginationModel inModel) {
    int page = inModel.page();
    int size = inModel.size();
    int total = products.size();
    int from = Math.min(page * size, total);
    var pageableEntity = new ProductPageEntity(
        new PaginationResponseEntity(page, size, (long) products.size()),
        products.subList(from, Math.min(from + size, total))
    );

    return toModel(pageableEntity);
  }

  @Override
  public ProductModel save(ProductModel inModel) {
    ProductEntity entity = new ProductEntity(//
        inModel.getId(), //
        inModel.getName(), //
        inModel.getPrice(), //
        inModel.getQuantity()//
    );
    //
    if(entity.getId() == null){
      entity.setId(idGen.getAndIncrement());
    }
    products.add(entity);
    //
    return toModel(entity);
  }

  @Override
  public void delete(ProductModel inModel){
    products.removeIf(p -> p.getId().equals(inModel.getId()));
  }



  public void ensureName(String name) {
    products.stream()
        .filter(p -> p.getName().equalsIgnoreCase(name))
        .findFirst()
        .orElseThrow(() -> new ConflictApiException("Conflicting name. name=" + name));
  }

  private ProductModel toModel(ProductEntity entity){
    return new ProductModel(//
        entity.getId(),//
        entity.getName(),//
        entity.getPrice(),//
        entity.getQuantity()//
    );
  }

  private ProductPageModel toModel(ProductPageEntity e){
    return null;
  }

}
