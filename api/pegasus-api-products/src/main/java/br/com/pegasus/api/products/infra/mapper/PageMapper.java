package br.com.pegasus.api.products.infra.mapper;

import br.com.pegasus.api.products.api.type.PaginationType;
import br.com.pegasus.api.products.domain.model.PageableModel;
import br.com.pegasus.api.products.domain.model.ProductPageModel;
import br.com.pegasus.api.products.infra.repository.entity.ProductEntity;
import org.springframework.data.domain.Page;

public final class PageMapper {

  public static PaginationType toType(PageableModel arg) {
    return PaginationType.builder()//
        .page(arg.getPage())//
        .size(arg.getSize())//
        .elements(arg.getElements())//
        .pages(arg.getPages())//
        .previous(arg.getPrevious())//
        .next(arg.getNext())//
        .build();
  }

  public static ProductPageModel toModel(Page<ProductEntity> arg) {
    return ProductPageModel.builder()//
        .pagination(createPageableModel(arg))//
        .products(arg.stream().map(ProductMapper::toModel).toList())//
        .build();
  }

  private static PageableModel createPageableModel(Page<ProductEntity> arg) {
    return PageableModel.builder()//
        .page(arg.getNumber())//
        .size(arg.getSize())//
        .elements(arg.getTotalElements())//
        .pages(arg.getTotalPages())//
        .previous(arg.hasPrevious())//
        .next(arg.hasNext())//
        .build();
  }

}
