package br.com.pegasus.api.products.api.type.product;

import br.com.pegasus.api.products.api.type.PaginationType;
import br.com.pegasus.api.products.infra.util.MethodUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ProductPageResponseType {

  private PaginationType pagination;
  private List<ProductResponseType> products;

  @Override
  public String toString() {
    return MethodUtil.toJson(this);
  }

}
