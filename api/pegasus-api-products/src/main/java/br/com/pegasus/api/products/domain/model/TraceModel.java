package br.com.pegasus.api.products.domain.model;

import br.com.pegasus.api.products.infra.util.MethodUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
public final class TraceModel {

  private final int index;
  private final Instant timestamp;
  private final String message;

  public TraceModel(int index, String message) {
    this.index = index;
    this.message = message;
    this.timestamp = Instant.now();
  }

  @Override
  public String toString() {
    return MethodUtil.toJson(this);
  }

}
