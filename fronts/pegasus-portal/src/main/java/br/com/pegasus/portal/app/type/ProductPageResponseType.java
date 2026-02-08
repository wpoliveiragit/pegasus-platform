package br.com.pegasus.portal.app.type;

import br.com.pegasus.portal.app.util.MethodUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductPageResponseType {

  private PaginationResponseType pagination;
  private List<ProductType> products;

  @Override
  public String toString() {
    return MethodUtil.toJson(this);
  }

}
