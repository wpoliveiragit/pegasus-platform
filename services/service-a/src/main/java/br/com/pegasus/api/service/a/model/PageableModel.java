package br.com.pegasus.api.service.a.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageableModel {
  private Integer page;
  private Integer size;
  private Long elements;
  private Integer pages;
  private Boolean previous;
  private Boolean next;
}
