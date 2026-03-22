package br.com.pegasus.web.portal.service;

import br.com.pegasus.web.portal.config.EnvConfig;
import br.com.pegasus.web.portal.config.prop.UrlProp;
import br.com.pegasus.web.portal.logger.AppLogger;
import br.com.pegasus.web.portal.model.ProductModel;
import br.com.pegasus.web.portal.model.RequestModel;
import br.com.pegasus.web.portal.service.port.ProductServicePort;
import br.com.pegasus.web.portal.type.ProductCreateBodyType;
import br.com.pegasus.web.portal.type.ProductUpdateRequestType;
import br.com.pegasus.web.portal.util.MethodUtil;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class ProductService implements ProductServicePort {

  private final static AppLogger log = new AppLogger(ProductService.class);
  private final RestTemplate restTemplate = new RestTemplate();
  private final UrlProp url;

  public ProductService(final EnvConfig envProp) {
    this.url = envProp.getUrl();
  }

  @Override
  public String callService(RequestModel inModel) {
    log.infoPattern("callService", "params: {}", inModel);

    try {
      log.infoPattern("callService", "restTemplate::request: url:{}", url.getProductsUp());
      ResponseEntity<Object> respObj = restTemplate.getForEntity(url.getProductsUp(), Object.class);
      String respJson = MethodUtil.toPrettyJson(respObj.getBody());
      log.infoPattern("callService", "restTemplate::response: {}", respJson);
      return respJson;
    } catch (Exception ex) {
      String respFailJson = ex.getMessage();
      log.infoPattern("callService", "fail: {}", respFailJson);
      return respFailJson;
    }
  }

  @Override
  public String findById(RequestModel inModel) {
    log.infoPattern("findById", "params: {}", inModel);
    Long id = inModel.getProduct().getId();

    try {
      log.infoPattern("findById", "restTemplate::request: url:{}, id:{}", url.getProductsById(), id);
      ResponseEntity<Object> respObj = restTemplate.getForEntity(url.getProductsById(), Object.class, id);
      String respJson = MethodUtil.toPrettyJson(respObj.getBody());
      log.infoPattern("findById", "restTemplate::response: {}", respJson);
      return respJson;
    } catch (Exception ex) {
      String respFailJson = ex.getMessage();
      log.infoPattern("findById", "fail: {}", respFailJson);
      return respFailJson;
    }
  }

  @Override
  public String findAll(RequestModel inModel) {
    log.infoPattern("findAll", "params: {}", inModel);
    Integer page = inModel.getPage();
    Integer size = inModel.getSize();

    try {
      log.infoPattern("findAll", "restTemplate::request: url:{}, page:{}, size:{}", url.getProductsPage(), page, size);
      ResponseEntity<Object> respObj = restTemplate.getForEntity(url.getProductsPage(), Object.class, page, size);
      String respJson = MethodUtil.toPrettyJson(respObj.getBody());
      log.infoPattern("findAll", "restTemplate::response {}", respJson);
      return respJson;
    } catch (Exception ex) {
      String respFailJson = ex.getMessage();
      log.infoPattern("findAll", "fail: {}", respFailJson);
      return respFailJson;
    }
  }

  @Override
  public String create(RequestModel inModel) {
    log.infoPattern("create", "params: {}", inModel);
    ProductModel product = inModel.getProduct();
    String name = product.getName();
    Float price = product.getPrice();
    Integer quantity = product.getQuantity();
    ProductCreateBodyType body = new ProductCreateBodyType(name, price, quantity);

    try {
      log.infoPattern("create", "restTemplate::request: url:{}, body:{}", url.getProducts(), body);
      ResponseEntity<Object> respObj = restTemplate.postForEntity(url.getProducts(), body, Object.class);
      String respJson = MethodUtil.toPrettyJson(respObj.getBody());
      log.infoPattern("create", "restTemplate::response {}", respJson);
      return respJson;
    } catch (Exception ex) {
      String respFailJson = ex.getMessage();
      log.infoPattern("create", "fail: {}", respFailJson);
      return respFailJson;
    }
  }

  @Override
  public String update(RequestModel inModel) {
    log.infoPattern("update", "params: {}", inModel);
    ProductModel product = inModel.getProduct();
    Long id = product.getId();
    ProductUpdateRequestType body = new ProductUpdateRequestType(product.getName(), product.getPrice(), product.getQuantity());

    try {
      log.infoPattern("update", "restTemplate::request: url:{}, id:{}, body:{}", url.getProductsById(), id, body);
      ResponseEntity<Object> respObj = restTemplate.exchange(url.getProductsById(), HttpMethod.PUT, new HttpEntity<>(body), Object.class, id);
      String respJson = MethodUtil.toPrettyJson(respObj.getBody());
      log.infoPattern("update", "restTemplate::response {}", respJson);
      return respJson;
    } catch (Exception ex) {
      String respFailJson = ex.getMessage();
      log.infoPattern("update", "fail: {}", respFailJson);
      return respFailJson;
    }
  }

  @Override
  public String delete(RequestModel inModel) {
    log.infoPattern("delete", "params: {}", inModel);
    Long id = inModel.getProduct().getId();

    try {
      log.infoPattern("delete", "restTemplate::request: url:{}, id:{}", url.getProductsById(), id);
      restTemplate.delete(url.getProductsById(), id);
      String respJson = MethodUtil.toPrettyJson(Map.of("OK", 200));
      log.infoPattern("delete", "restTemplate::response {}", respJson);
      return respJson;
    } catch (Exception ex) {
      String respFailJson = ex.getMessage();
      log.infoPattern("delete", "fail: {}", respFailJson);
      return respFailJson;
    }
  }

}
