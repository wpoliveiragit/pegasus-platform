package br.com.pegasus.web.portal.domain.model;

import br.com.pegasus.web.portal.infra.util.MethodUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PageModel {
  public final Integer page;
  public final Integer size;


  @Override
  public String toString() {
    return MethodUtil.toJson(this);
  }

}
