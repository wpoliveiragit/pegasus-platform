package br.com.pegasus.web.portal.domain.type;

import br.com.pegasus.web.portal.domain.model.ProductModel;
import br.com.pegasus.web.portal.infra.util.MethodUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseType {

  private int status;
  private String errorMsg;
  private ProductModel product;


  public ProductResponseType(int status, ProductModel product) {
    this.status = status;
    this.product = product;
  }

  public ProductResponseType(int status, String msgError) {
    this.status = status;
    this.errorMsg = msgError;
  }

  @Override
  public String toString() {
    return MethodUtil.toJson(this);
  }

}
