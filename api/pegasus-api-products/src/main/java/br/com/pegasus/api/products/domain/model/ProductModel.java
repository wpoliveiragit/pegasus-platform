package br.com.pegasus.api.products.domain.model;

import br.com.pegasus.api.products.infra.util.MethodUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductModel {

  private Long id;
  private String name;
  private Float price;
  private Integer quantity;

  public ProductModel(Long id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return MethodUtil.toJson(this);
  }

}
