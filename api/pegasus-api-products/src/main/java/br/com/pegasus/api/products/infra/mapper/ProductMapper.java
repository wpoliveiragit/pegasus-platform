package br.com.pegasus.api.products.infra.mapper;

import br.com.pegasus.api.products.api.type.ProductCreateRequestType;
import br.com.pegasus.api.products.api.type.ProductPageResponseType;
import br.com.pegasus.api.products.api.type.ProductResponseType;
import br.com.pegasus.api.products.api.type.ProductUpdateRequestType;
import br.com.pegasus.api.products.domain.model.ProductModel;
import br.com.pegasus.api.products.domain.model.ProductPageModel;
import br.com.pegasus.api.products.infra.repository.entity.ProductEntity;

public final class ProductMapper {

  // TYPE
  public static ProductPageResponseType toType(ProductPageModel model) {
    var type = new ProductPageResponseType();
    type.setPagination(PageMapper.toType(model.getPagination()));
    type.setProducts(model.getProducts().stream().map(ProductMapper::toType).toList());
    return type;
  }

  // ENTITY
  public static ProductEntity toEntity(ProductModel model) {
    var entity = new ProductEntity();
    entity.setId(model.getId());
    entity.setName(model.getName());
    entity.setPrice(model.getPrice());
    entity.setQuantity(model.getQuantity());
    return entity;
  }

  public static ProductResponseType toType(ProductModel model) {
    var type = new ProductResponseType();
    type.setId(model.getId());
    type.setName(model.getName());
    type.setPrice(model.getPrice());
    type.setQuantity(model.getQuantity());
    return type;
  }

  // MODEL
  public static ProductModel toModel(long id, ProductUpdateRequestType type) {
    var model = new ProductModel();
    model.setId(id);
    model.setName(type.getName());
    model.setPrice(type.getPrice());
    model.setQuantity(type.getQuantity());
    return model;
  }

  public static ProductModel toModel(ProductCreateRequestType type) {
    var model = new ProductModel();
    model.setName(type.getName());
    model.setPrice(type.getPrice());
    model.setQuantity(type.getQuantity());
    return model;
  }

  public static ProductModel toModel(ProductEntity entity) {
    var model = new ProductModel();
    model.setId(entity.getId());
    model.setName(entity.getName());
    model.setPrice(entity.getPrice());
    model.setQuantity(entity.getQuantity());
    return model;
  }

}
