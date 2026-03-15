package br.com.pegasus.web.portal.model;

import br.com.pegasus.web.portal.util.MethodUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestModel {

//  private int code; // http-status::value
//  private String message; // http-status::reason-phrase
//  private String detail; // Detalhes do problema

  private Long id;
  private String name;
  private Float price;
  private Integer quantity;

  public ProductRequestModel(long id){
    this.id = id;
  }

  @Override
  public String toString() {
    return MethodUtil.toJson(this);
  }

}
