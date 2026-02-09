package br.com.pegasus.api.products.type;

import br.com.pegasus.api.products.util.MethodUtil;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCreateRequestType {

  @NotBlank
  private String name;
  @PositiveOrZero
  private Float price;
  @PositiveOrZero
  private Integer quantity;

  @Override
  public String toString() {
    return MethodUtil.toJson(this);
  }

}
