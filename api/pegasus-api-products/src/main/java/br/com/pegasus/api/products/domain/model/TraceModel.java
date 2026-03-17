package br.com.pegasus.api.products.domain.model;

import br.com.pegasus.api.products.infra.util.MethodUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
public final class TraceModel {
  /**Uso futuro, com kafka - não deletar*/

  private final int index;
  private final String message;
  private final Instant timestamp = Instant.now();

  @Override
  public String toString() {
    return MethodUtil.toJson(this);
  }


  /**
   * Exemplo de uso
   *
   * private int totalTracking = 0;
   * private final List<TraceModel> traces = new ArrayList<>();
   *
   * public void addTrace(String message, Object... args) {
   *   traces.add(new TraceModel(++totalTracking, MessageFormatter.arrayFormat(message, args).getMessage()));
   * }
   */

}
