package br.com.pegasus.api.products.domain.model;

import br.com.pegasus.api.products.infra.util.MethodUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductPageModel {

  private PageableModel pagination;
  private List<ProductModel> products;

  @Override
  public String toString() {
    return MethodUtil.toJson(this);
  }

}
