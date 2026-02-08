package br.com.pegasus.api.products.model;

import br.com.pegasus.api.products.util.MethodUtil;

public record PaginationModel(Integer page, Integer size) {

  @Override
  public String toString() {
    return MethodUtil.toJson(this);
  }

}
