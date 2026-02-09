package br.com.pegasus.api.products.type;

import br.com.pegasus.api.products.util.MethodUtil;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductType {

  private Long id;
  private String name;
  private Float price;
  private Integer quantity;

  @Override
  public String toString() {
    return MethodUtil.toJson(this);
  }

}
