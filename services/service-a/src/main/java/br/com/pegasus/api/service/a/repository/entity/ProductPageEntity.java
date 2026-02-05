package br.com.pegasus.api.service.a.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@AllArgsConstructor
@Getter
@Setter
public class ProductPageEntity {
  private PaginationResponseEntity pagination;
  private List<ProductEntity> products;
}
