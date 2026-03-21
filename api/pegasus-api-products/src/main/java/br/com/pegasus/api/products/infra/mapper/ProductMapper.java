package br.com.pegasus.api.products.infra.mapper;

import br.com.pegasus.api.products.api.type.product.ProductCreateRequestType;
import br.com.pegasus.api.products.api.type.product.ProductPageResponseType;
import br.com.pegasus.api.products.api.type.product.ProductResponseType;
import br.com.pegasus.api.products.api.type.product.ProductUpdateRequestType;
import br.com.pegasus.api.products.domain.model.ProductModel;
import br.com.pegasus.api.products.domain.model.ProductPageModel;
import br.com.pegasus.api.products.infra.repository.entity.ProductEntity;

public final class ProductMapper {

  // TYPE
  public static ProductPageResponseType toType(ProductPageModel arg) {
    return ProductPageResponseType.builder()//
        .pagination(PageMapper.toType(arg.getPagination()))//
        .products(arg.getProducts().stream().map(ProductMapper::toType).toList())//
        .build();
  }

  // ENTITY
  public static ProductEntity toEntity(ProductModel arg) {
    return ProductEntity.builder()//
        .id(arg.getId())//
        .name(arg.getName())//
        .price(arg.getPrice())//
        .quantity(arg.getQuantity())//
        .build();
  }

  public static ProductResponseType toType(ProductModel arg) {
    return ProductResponseType.builder()//
        .id(arg.getId())//
        .name(arg.getName())//
        .price(arg.getPrice())//
        .quantity(arg.getQuantity())//
        .build();
  }

  // MODEL
  public static ProductModel toModel(long id, ProductUpdateRequestType arg) {
    return ProductModel.builder()//
        .id(id)//
        .name(arg.getName())//
        .price(arg.getPrice())//
        .quantity(arg.getQuantity())//
        .build();
  }

  public static ProductModel toModel(ProductCreateRequestType arg) {
    return ProductModel.builder()//
        .name(arg.getName())//
        .price(arg.getPrice())//
        .quantity(arg.getQuantity())//
        .build();
  }

  public static ProductModel toModel(ProductEntity arg) {
    return ProductModel.builder()//
        .id(arg.getId())//
        .name(arg.getName())//
        .price(arg.getPrice())//
        .quantity(arg.getQuantity())//
        .build();
  }

}
