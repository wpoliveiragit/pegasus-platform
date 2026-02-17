package br.com.pegasus.api.products.api.type;

import br.com.pegasus.api.products.infra.util.MethodUtil;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponseType {

  private Long id;
  private String name;
  private Float price;
  private Integer quantity;

  @Override
  public String toString() {
    return MethodUtil.toJson(this);
  }

}
