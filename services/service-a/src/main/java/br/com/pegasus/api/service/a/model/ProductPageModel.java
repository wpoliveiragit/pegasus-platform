package br.com.pegasus.api.service.a.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class ProductPageModel {
  private PageableModel pagination;
  private List<ProductModel> products;
}
