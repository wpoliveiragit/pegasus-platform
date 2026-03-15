package br.com.test;

import br.com.pegasus.web.portal.type.PaginationType;
import br.com.pegasus.web.portal.type.ProductPageResponseModel;
import br.com.pegasus.web.portal.model.ProductRequestModel;

import java.util.List;

public class Main {

  public static void main(String[] args) {

    var p = new ProductRequestModel();


    ProductPageResponseModel v = new ProductPageResponseModel();
    v.setPagination(new
        PaginationType(1, 1, 1L, 1, true, true));
    v.setProducts(List.of(new ProductRequestModel(1L, "Name", 1.1f, 1)));

    System.out.println(p);
  }

}
