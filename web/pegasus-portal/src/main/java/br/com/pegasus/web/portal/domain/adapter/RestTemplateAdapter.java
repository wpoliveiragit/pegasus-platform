package br.com.pegasus.web.portal.domain.adapter;

import br.com.pegasus.web.portal.domain.model.ResponseModel;

public interface RestTemplateAdapter {

  <R, E> ResponseModel<R, E> getForEntity(
      String url,
      Class<R> successResponseType,
      Class<E> errorResponseType,
      Object... uriVariables
  );

  <R, E> ResponseModel<R, E> postForEntity(
      String url,
      Object body,
      Class<R> successResponse,
      Class<E> failResponse
  );

  <R, E> ResponseModel<R, E> exchange(
      String url,
      Object body,
      Class<R> successResponse,
      Class<E> failResponse,
      Object... uriVariables
  );

  void delete(String url, Object... uriVariables);
}
