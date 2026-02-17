package br.com.pegasus.api.products.infra.repository.entity;

import br.com.pegasus.api.products.infra.util.MethodUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ProductPageEntity {

  private PaginationResponseEntity pagination;
  private List<ProductEntity> products;

  @Override
  public String toString() {
    return MethodUtil.toJson(this);
  }

}
