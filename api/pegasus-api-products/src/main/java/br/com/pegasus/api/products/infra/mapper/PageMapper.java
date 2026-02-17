package br.com.pegasus.api.products.infra.mapper;

import br.com.pegasus.api.products.domain.model.PageableModel;
import br.com.pegasus.api.products.domain.model.ProductPageModel;
import br.com.pegasus.api.products.infra.repository.entity.ProductEntity;
import br.com.pegasus.api.products.api.type.PaginationType;
import org.springframework.data.domain.Page;

public final class PageMapper {

  public static PaginationType toType(PageableModel model) {
    var type = new PaginationType();
    type.setPage(model.getPage());
    type.setSize(model.getSize());
    type.setElements(model.getElements());
    type.setPages(model.getPages());
    type.setPrevious(model.getPrevious());
    type.setNext(model.getNext());
    return type;
  }

  public static ProductPageModel toModel(Page<ProductEntity> entity) {
    var model = new ProductPageModel();
    model.setPagination(createPageableModel(entity));
    model.setProducts(entity.stream().map(ProductMapper::toModel).toList());
    return model;
  }

  private static PageableModel createPageableModel(Page<ProductEntity> entity) {
    var pag = new PageableModel();
    pag.setPage(entity.getNumber());
    pag.setSize(entity.getSize());
    pag.setElements(entity.getTotalElements());
    pag.setPages(entity.getTotalPages());
    pag.setPrevious(entity.hasPrevious());
    pag.setNext(entity.hasNext());
    return pag;
  }

}
