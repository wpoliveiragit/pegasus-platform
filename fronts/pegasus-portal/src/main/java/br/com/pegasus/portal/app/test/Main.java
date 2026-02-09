package br.com.pegasus.portal.app.test;

import br.com.pegasus.portal.app.type.PaginationType;
import br.com.pegasus.portal.app.type.ProductPageResponseType;
import br.com.pegasus.portal.app.type.ProductType;

import java.util.List;

public class Main {

  public static void main(String[] args) {

    var p = new ProductType();


    ProductPageResponseType v = new ProductPageResponseType();
    v.setPagination(new
        PaginationType(1, 1, 1L, 1, true, true));
    v.setProducts(List.of(new ProductType(1L, "Name", 1.1f, 1)));

    System.out.println(p);
  }

}
