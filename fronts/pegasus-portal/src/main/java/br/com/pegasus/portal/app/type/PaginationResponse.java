package br.com.pegasus.portal.app.type;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginationResponse {
  private Integer page;
  private Integer size;
  private Long elements;
  private Integer pages;
  private Boolean previous;
  private Boolean next;
}
