package br.com.pegasus.portal.app.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@AllArgsConstructor
@Getter
@Setter
public class ProductPageResponseType {
  private PaginationResponse pagination;
  private List<ProductRequestType> products;
}
