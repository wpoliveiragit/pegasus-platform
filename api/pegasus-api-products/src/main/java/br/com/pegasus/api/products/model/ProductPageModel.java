package br.com.pegasus.api.products.model;

import br.com.pegasus.api.products.util.MethodUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class ProductPageModel {

  private PageableModel pagination;
  private List<ProductModel> products;

  @Override
  public String toString() {
    return MethodUtil.toJson(this);
  }

}
