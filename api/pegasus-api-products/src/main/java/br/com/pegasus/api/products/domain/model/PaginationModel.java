package br.com.pegasus.api.products.domain.model;

import br.com.pegasus.api.products.infra.util.MethodUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PaginationModel {

  private Integer page;
  private Integer size;

  @Override
  public String toString() {
    return MethodUtil.toJson(this);
  }

}
