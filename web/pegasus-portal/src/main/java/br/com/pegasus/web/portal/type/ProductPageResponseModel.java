package br.com.pegasus.web.portal.type;

import br.com.pegasus.web.portal.model.ProductRequestModel;
import br.com.pegasus.web.portal.util.MethodUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductPageResponseModel {

  private PaginationType pagination;
  private List<ProductRequestModel> products;

  @Override
  public String toString() {
    return MethodUtil.toJson(this);
  }

}
