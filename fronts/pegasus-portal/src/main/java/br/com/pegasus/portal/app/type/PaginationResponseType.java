package br.com.pegasus.portal.app.type;

import br.com.pegasus.portal.app.util.MethodUtil;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginationResponseType {

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
