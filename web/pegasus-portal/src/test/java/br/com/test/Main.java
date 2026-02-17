package br.com.test;

import br.com.pegasus.web.portal.domain.type.PaginationType;
import br.com.pegasus.web.portal.domain.type.ProductPageResponseModel;
import br.com.pegasus.web.portal.domain.model.ProductModel;

import java.util.List;

public class Main {

  public static void main(String[] args) {

    var p = new ProductModel();


    ProductPageResponseModel v = new ProductPageResponseModel();
    v.setPagination(new
        PaginationType(1, 1, 1L, 1, true, true));
    v.setProducts(List.of(new ProductModel(1L, "Name", 1.1f, 1)));

    System.out.println(p);
  }

}
