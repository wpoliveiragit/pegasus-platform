package br.com.pegasus.api.products.type;

import br.com.pegasus.api.products.util.MethodUtil;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequestType {
  private Long id;
  private String name;
  private Float price;
  private Integer quantity;

  public ProductRequestType(Long id, String name, Float price, Integer quantity) {
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
