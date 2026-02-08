package br.com.pegasus.api.products.type;

import br.com.pegasus.api.products.util.MethodUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponseType {

  private Long id;
  private String name;
  private Float price;
  private Integer quantity;

  public ProductResponseType(Long id, String name, Float price, Integer quantity) {
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
