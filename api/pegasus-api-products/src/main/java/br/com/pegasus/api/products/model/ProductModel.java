package br.com.pegasus.api.products.model;

import br.com.pegasus.api.products.util.MethodUtil;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductModel {

  private Long id;
  private String name;
  private Float price;
  private Integer quantity;

  public ProductModel(Long id) {
    this.id = id;
  }

  public ProductModel(String name, Float price, Integer quantity) {
    this.name = name;
    this.price = price;
    this.quantity = quantity;
  }

  public ProductModel(Long id, String name, Float price, Integer quantity) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.quantity = quantity;
  }

  @Override
  public String toString() {
    return MethodUtil.toJson(this);
  }

}
