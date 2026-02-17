package br.com.pegasus.web.portal.domain.type;

import br.com.pegasus.web.portal.infra.util.MethodUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestType {

  private Long id;
  private String name;
  private Float price;
  private Integer quantity;

  @Override
  public String toString() {
    return MethodUtil.toJson(this);
  }

}
