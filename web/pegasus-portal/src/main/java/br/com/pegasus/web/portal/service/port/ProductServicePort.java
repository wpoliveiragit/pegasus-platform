package br.com.pegasus.web.portal.service.port;

import br.com.pegasus.web.portal.model.ProductModel;
import br.com.pegasus.web.portal.model.RequestModel;

public interface ProductServicePort {
  String callService(RequestModel inModel);
  String findById(RequestModel inModel);
  String findAll(RequestModel inModel);
  String create(RequestModel inModel);
  String update(RequestModel inModel);
  String delete(RequestModel inModel);
}
