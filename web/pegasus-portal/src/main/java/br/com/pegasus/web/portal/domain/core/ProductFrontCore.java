package br.com.pegasus.web.portal.domain.core;

import br.com.pegasus.web.portal.domain.adapter.RestTemplateAdapter;
import br.com.pegasus.web.portal.domain.model.PageModel;
import br.com.pegasus.web.portal.domain.model.ProductModel;
import br.com.pegasus.web.portal.domain.port.ProductServicePort;
import br.com.pegasus.web.portal.domain.service.adapter.RestTemplateImplAdapter;
import br.com.pegasus.web.portal.domain.type.ExceptionResponseModel;
import br.com.pegasus.web.portal.domain.type.ProductPageResponseModel;
import br.com.pegasus.web.portal.domain.type.ProductPageResponseType;
import br.com.pegasus.web.portal.domain.type.ProductResponseType;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

public class ProductFrontCore implements ProductServicePort {

  private static final String BASE_URL = "http://gateway:8080/pegasus-api-products/products";
  private static final String getAllUrl = BASE_URL + "?page={page}&size={size}";
  private static final String BASE_URL_WITH_ID = BASE_URL + "/{id}";

  private final RestTemplateAdapter restTemplate;

  public ProductFrontCore(RestTemplateAdapter restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Override
  public ProductPageResponseType getAll(PageModel model) {
    var entities = restTemplate.getForEntity(getAllUrl, ProductPageResponseModel.class, ExceptionResponseModel.class, model.getPage(), model.getSize());

    var respT = new ProductPageResponseType(entities.getStatus());
    if (checkHttpStatus(entities.getStatus())) { // ok
      var respM = entities.getResponse();
      respT.setPagination(respM.getPagination());
      respT.setProducts(respM.getProducts());
    } else { //error
      ExceptionResponseModel response = entities.getErrorResponse();
      respT.setMessage(response.getDetail());
    }
    return respT;
  }

  @Override
  public ProductResponseType getOne(ProductModel model) {
    var entity = restTemplate.getForEntity(BASE_URL_WITH_ID, ProductModel.class, ExceptionResponseModel.class, model.getId());
    int status = entity.getStatus();
    return checkHttpStatus(status) ? new ProductResponseType(status, entity.getResponse()) : new ProductResponseType(status, entity.getErrorMessage());
  }

  @Override
  public ProductResponseType create(ProductModel model) {
    //deve ter um body pra create reservado
    var entity = restTemplate.postForEntity(BASE_URL, new HttpEntity<>(model), ProductModel.class, ExceptionResponseModel.class);
    int status = entity.getStatus();
    return checkHttpStatus(status) ? new ProductResponseType(status, entity.getResponse()) : new ProductResponseType(status, entity.getErrorMessage());
  }

  @Override
  public ProductResponseType update(ProductModel model) {
    //deve ter um body pra update reservado
    var entity = restTemplate.exchange(BASE_URL_WITH_ID, model, ProductModel.class, ExceptionResponseModel.class, model.getId());
    int status = entity.getStatus();
    return checkHttpStatus(status) ? new ProductResponseType(status, entity.getResponse()) : new ProductResponseType(status, entity.getErrorMessage());
  }

  @Override
  public void delete(ProductModel model) {
    restTemplate.delete(BASE_URL_WITH_ID, model.getId());
  }

  private boolean checkHttpStatus(int value) {
    return (value * .01) == 2;
  }

}
