package br.com.pegasus.api.products.repository.entity;

import br.com.pegasus.api.products.util.MethodUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PaginationResponseEntity {

  private Integer page;
  private Integer size;
  private Long elements;
  private Integer pages;
  private Boolean previous;
  private Boolean next;

  @Override
  public String toString() {
    return MethodUtil.toJson(this);
  }

}
