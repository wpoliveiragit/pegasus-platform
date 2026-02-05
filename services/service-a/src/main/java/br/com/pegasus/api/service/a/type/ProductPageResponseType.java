package br.com.pegasus.api.service.a.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class ProductPageResponseType {
  private PaginationResponseType pagination;
  private List<ProductResponseType> products;

  public ProductPageResponseType(int page, int size, int total, List<ProductResponseType> products) {
    int pages = (int) Math.ceil((double) total / size);

    pagination = new PaginationResponseType();
    pagination.setPage(page);
    pagination.setSize(size);
    pagination.setElements((long) total);
    pagination.setPages(pages);
    pagination.setPrevious(page > 0);
    pagination.setNext(page < pages - 1);
    this.products = products;
  }
}
