package br.com.pegasus.api.products.mapper;

import br.com.pegasus.api.products.model.PageableModel;
import br.com.pegasus.api.products.model.ProductModel;
import br.com.pegasus.api.products.model.ProductPageModel;
import br.com.pegasus.api.products.repository.entity.ProductEntity;
import br.com.pegasus.api.products.type.PaginationType;
import br.com.pegasus.api.products.type.ProductCreateRequestType;
import br.com.pegasus.api.products.type.ProductPageResponseType;
import br.com.pegasus.api.products.type.ProductResponseType;
import br.com.pegasus.api.products.type.ProductUpdateRequestType;

public final class ProductMapper {

  //type
  public static ProductPageResponseType toType(ProductPageModel outModel) {
    PageableModel pagM = outModel.getPagination();
    return new ProductPageResponseType(new PaginationType(//
        pagM.getPage(),//
        pagM.getSize(),//
        pagM.getElements(),//
        pagM.getPages(),//
        pagM.getPrevious(),//
        pagM.getNext()//
    ),//
        outModel.getProducts()//
            .stream()//
            .map(ProductMapper::toType)//
            .toList());
  }

  public static ProductResponseType toType(ProductModel inModel) {
    return new ProductResponseType(//
        inModel.getId(),//
        inModel.getName(),//
        inModel.getPrice(),//
        inModel.getQuantity()//
    );
  }

  // model
  public static ProductModel toModel(Long id, ProductUpdateRequestType obj) {
    return new ProductModel(//
        id,//
        obj.getName(),//
        obj.getPrice(),//
        obj.getQuantity()//
    );
  }

  public static ProductModel toModel(ProductCreateRequestType obj) {
    return new ProductModel(//
        obj.getName(),//
        obj.getPrice(),//
        obj.getQuantity()//
    );
  }

  public static ProductModel toModel(ProductEntity obj) {
    return new ProductModel(//
        obj.getId(),//
        obj.getName(),//
        obj.getPrice(),//
        obj.getQuantity()//
    );
  }

  //entity
  public static ProductEntity toEntity(ProductModel obj) {
    return new ProductEntity(//
        obj.getId(),//
        obj.getName(),//
        obj.getPrice(),//
        obj.getQuantity()//
    );
  }

}
