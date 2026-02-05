package br.com.pegasus.api.service.a.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PaginationResponseType {
  private Integer page;
  private Integer size;
  private Long elements;
  private Integer pages;
  private Boolean previous;
  private Boolean next;
}
