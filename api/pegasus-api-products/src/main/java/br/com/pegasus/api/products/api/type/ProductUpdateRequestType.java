package br.com.pegasus.api.products.api.type;

import br.com.pegasus.api.products.infra.util.MethodUtil;
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

  @Override
  public String toString() {
    return MethodUtil.toJson(this);
  }

}
