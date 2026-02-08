package br.com.pegasus.api.products.repository.entity;

import br.com.pegasus.api.products.util.MethodUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class ProductPageEntity {

  private PaginationResponseEntity pagination;
  private List<ProductEntity> products;

  @Override
  public String toString() {
    return MethodUtil.toJson(this);
  }

}
