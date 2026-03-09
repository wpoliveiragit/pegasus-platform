package br.com.pegasus.web.portal.domain.service;

import br.com.pegasus.web.portal.domain.adapter.RestTemplateAdapter;
import br.com.pegasus.web.portal.domain.core.ProductFrontCore;
import br.com.pegasus.web.portal.domain.model.PageModel;
import br.com.pegasus.web.portal.domain.model.ProductModel;
import br.com.pegasus.web.portal.domain.port.ProductServicePort;
import br.com.pegasus.web.portal.domain.type.ProductPageResponseType;
import br.com.pegasus.web.portal.domain.type.ProductResponseType;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Log4j2
@Service
public class ProductService implements ProductServicePort {

  public final ProductServicePort core;

  public ProductService(RestTemplateAdapter restTemplate) {
    this.core = new ProductFrontCore(restTemplate);
  }

  @Override
  public ProductPageResponseType getAll(PageModel model) {
    log.info("ProductService::getAll::started: {}", model);
    var response = core.getAll(model);
    log.info("ProductService::getAll::ended: {}", response);
    return response;
  }

  @Override
  public ProductResponseType getOne(ProductModel model) {
    log.info("ProductService::getOne::started: {}", model);
    var response = core.getOne(model);
    log.info("ProductService::getOne::ended: {}", response);
    return response;
  }

  @Override
  public ProductResponseType create(ProductModel model) {
    log.info("ProductService::create::started: {}", model);
    var response = core.getOne(model);
    log.info("ProductService::create::ended: {}", response);
    return response;
  }

  @Override
  public ProductResponseType update(ProductModel model) {
    log.info("ProductService::update::started: {}", model);
    var response = core.update(model);
    log.info("ProductService::update::ended: {}", response);
    return response;
  }

  @Override
  public void delete(ProductModel model) {
    log.info("ProductService::delete::started: {}", model);
    core.delete(model);
    log.info("ProductService::delete::ended: void");
  }

}
