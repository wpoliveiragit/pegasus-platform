package br.com.pegasus.web.portal.config;

import br.com.pegasus.web.portal.config.prop.UrlProp;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "api.products")
@Getter
@Setter
public class EnvConfig {

  private UrlProp url;

}
