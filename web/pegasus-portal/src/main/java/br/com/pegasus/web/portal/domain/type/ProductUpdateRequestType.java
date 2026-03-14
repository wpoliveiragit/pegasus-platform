package br.com.pegasus.web.portal.domain.type;

import br.com.pegasus.web.portal.infra.util.MethodUtil;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
