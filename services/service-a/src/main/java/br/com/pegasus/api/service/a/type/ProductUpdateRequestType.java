package br.com.pegasus.api.service.a.type;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductUpdateRequestType {
  @NotBlank
  private String name;
  @PositiveOrZero
  private Float price;
  @PositiveOrZero
  private Integer quantity;

  public ProductUpdateRequestType(String name, Float price, Integer quantity) {
    this.name = name;
    this.price = price;
    this.quantity = quantity;
  }

}
