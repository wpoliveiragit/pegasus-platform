package br.com.pegasus.web.portal.logger;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.message.ParameterizedMessage;

@Log4j2
public class AppLogger {
  private final String className;

  public AppLogger(Class<?> c) {
    className = c.getSimpleName();
  }

  public void info(String message, Object... params) {
    log.info(message, params);
  }

  public void warn(String message, Object... params) {
    log.warn(message, params);
  }

  public void erro(String message, Object... params) {
    log.error(message, params);
  }

  // TEST

  /**
   * É criado uma string onde ela seguirá o padrão:
   * <pre>
   * "{className}::{methodName}::{patternParams}"
   *
   * onde:
   * - className: Obtido ao instanciar um objeto desta classe
   * - methodName: obtido no primeiro parametro deste método
   * - patternParams: obtido no segundo parametro deste método
   *
   * Exemplo de uso
   * AppLogger log = new AppLogger(XPTOClass.class);
   *
   * void methodX(){
   *    log.infoPattern("methodX", "params: id:{}, name:{}", 123,"Meu Nome")
   * }
   *
   * Formatação final da mensagem do exemplo acima: "XPTOClass::methodX::params: id:123, name:Meu Nome"
   *
   * </pre>
   *
   * @param methodName nome do método.
   * @param patternParams padrãoo
   * @param args dados a serem adicionados no patternParams
   */
  public void infoPattern(String methodName, String patternParams, Object... args) {
    String message = ParameterizedMessage.format("{}::{}::{}", new Object[]{className, methodName, patternParams});
    log.info(message, args);
  }


}
