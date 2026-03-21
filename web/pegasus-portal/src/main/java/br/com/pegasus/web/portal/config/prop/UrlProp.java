package br.com.pegasus.web.portal.config.prop;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UrlProp {

  private String products;

  public String getProductsById() {
    return products + "/{id}";
  }

  public String getProductsPage() {
    return products + "?page={page}&size={size}";
  }

  public String getProductsUp() {
    return products + "/up";
  }

}
