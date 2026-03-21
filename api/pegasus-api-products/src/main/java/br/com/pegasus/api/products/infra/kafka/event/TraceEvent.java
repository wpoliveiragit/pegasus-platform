package br.com.pegasus.api.products.infra.kafka.event;

import br.com.pegasus.api.products.infra.util.MethodUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public final class TraceEvent {
  /** Uso futuro, com kafka - não deletar */

  private int index;
  private String message;
  private Instant timestamp = Instant.now();

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
