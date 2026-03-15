package br.com.pegasus.web.portal.service;

import br.com.pegasus.web.portal.logger.AppLogger;
import br.com.pegasus.web.portal.logger.port.AppLoggerPort;
import br.com.pegasus.web.portal.model.ProductRequestModel;
import br.com.pegasus.web.portal.service.port.ProductServicePort;
import br.com.pegasus.web.portal.type.ProductResponseModel;
import br.com.pegasus.web.portal.util.MethodUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class ProductService implements ProductServicePort {
  private static final AppLoggerPort log = new AppLogger();

  private final RestTemplate restTemplate = new RestTemplate();

  @Override
  public ProductResponseModel findById(ProductRequestModel inModel) {
    log.info("ProductService::getOne::params: inModel:{}", inModel);
    Long id = inModel.getId();
    var resp = new ProductResponseModel();

    try {
      log.info("ProductService::getOne::restTemplate::request: URL:{} -  id:{}", ConstService.URL_ID, id);
      ResponseEntity<Object> response = restTemplate.getForEntity(ConstService.URL_ID, Object.class, inModel.getId());
      log.info("ProductService::getOne::restTemplate::response: success:{}", response);
      resp.setJsonResponse(MethodUtil.toPrettyJson(response.getBody()));
    } catch (Exception ex) {
      String jsonError = MethodUtil.toPrettyJson(Map.of("", ex.getMessage()));
      log.warn("ProductService::getOne::restTemplate:: error:{}", jsonError);
      resp.setJsonErrorResponse(jsonError);
    }
    log.info("ProductService::getOne::response: {}", resp);
    return resp;
  }

}
