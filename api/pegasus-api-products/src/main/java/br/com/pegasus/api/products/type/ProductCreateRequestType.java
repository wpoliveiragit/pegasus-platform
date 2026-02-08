package br.com.pegasus.api.products.type;

import br.com.pegasus.api.products.util.MethodUtil;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductCreateRequestType {

  @NotBlank private String name;
  @PositiveOrZero private Float price;
  @PositiveOrZero private Integer quantity;

  public ProductCreateRequestType(String name, Float price, Integer quantity) {
    this.name = name;
    this.price = price;
    this.quantity = quantity;
  }

  @Override
  public String toString(){
    return MethodUtil.toJson(this);
  }

}
