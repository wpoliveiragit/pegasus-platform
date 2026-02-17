package br.com.pegasus.web.portal.domain.model;

import br.com.pegasus.web.portal.infra.util.MethodUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductModel {

  private Long id;
  private String name;
  private Float price;
  private Integer quantity;

  public ProductModel(long id){
    this.id = id;
  }

  @Override
  public String toString() {
    return MethodUtil.toJson(this);
  }

}
