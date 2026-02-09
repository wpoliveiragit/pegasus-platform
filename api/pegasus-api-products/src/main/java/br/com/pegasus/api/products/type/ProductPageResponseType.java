package br.com.pegasus.api.products.type;

import br.com.pegasus.api.products.util.MethodUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ProductPageResponseType {

  private PaginationType pagination;
  private List<ProductResponseType> products;

  @Override
  public String toString() {
    return MethodUtil.toJson(this);
  }

}
