package br.com.pegasus.api.service.a.repository.entity;

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

  public PaginationResponseEntity(Integer page, Integer size, Long total) {
    this.page = page;
    this.size = size;
    this.elements = total;
    this.pages = (int) Math.ceil((double) total / size);
    this.previous = page > 0;
    this.next = page < pages - 1;
  }
}
