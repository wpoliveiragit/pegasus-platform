package br.com.pegasus.api.products.api.type;

import br.com.pegasus.api.products.infra.util.MethodUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductPageResponseType {

  private PaginationType pagination;
  private List<ProductResponseType> products;

  @Override
  public String toString() {
    return MethodUtil.toJson(this);
  }

}
