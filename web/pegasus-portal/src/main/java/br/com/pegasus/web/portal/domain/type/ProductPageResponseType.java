package br.com.pegasus.web.portal.domain.type;

import br.com.pegasus.web.portal.domain.model.ProductModel;
import br.com.pegasus.web.portal.infra.util.MethodUtil;
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

  private int status;
  private String message;
  private PaginationType pagination;
  private List<ProductModel> products;

  public ProductPageResponseType(int status){
    this.status = status;
  }

  @Override
  public String toString() {
    return MethodUtil.toJson(this);
  }

}
