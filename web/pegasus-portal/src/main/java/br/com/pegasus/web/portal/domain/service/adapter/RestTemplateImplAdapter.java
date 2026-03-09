package br.com.pegasus.web.portal.domain.service.adapter;

import br.com.pegasus.web.portal.domain.adapter.RestTemplateAdapter;
import br.com.pegasus.web.portal.domain.model.ResponseModel;
import br.com.pegasus.web.portal.infra.util.MethodUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.function.Supplier;

@Log4j2
@Component
public class RestTemplateImplAdapter implements RestTemplateAdapter {

  private final RestTemplate restTemplate = new RestTemplate();

  @Override
  public <R, E> ResponseModel<R, E> getForEntity(String url, Class<R> successResponse, Class<E> failResponse, Object... uriVariables) {
    return send(failResponse, () -> restTemplate.getForEntity(url, successResponse, uriVariables));
  }

  @Override
  public <R, E> ResponseModel<R, E> postForEntity(String url, Object body, Class<R> successResponse, Class<E> failResponse) {
    return send(failResponse, () -> restTemplate.postForEntity(url, new HttpEntity<>(body), successResponse));
  }

  @Override
  public <R, E> ResponseModel<R, E> exchange(String url, Object body, Class<R> successResponse, Class<E> failResponse, Object... uriVariables) {
    return send(failResponse, () -> restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(body), successResponse, uriVariables));
  }

  @Override
  public void delete(String url, Object... uriVariables) {
    restTemplate.delete(url, uriVariables);
  }

  private static <R, E> ResponseModel<R, E> send(Class<E> errorType, Supplier<ResponseEntity<R>> action) {
    try {
      ResponseEntity<R> response = action.get();
      return new ResponseModel<>(response.getStatusCode().value(), response.getBody());
    } catch (HttpClientErrorException | HttpServerErrorException ex) {
      return new ResponseModel<>(//
          ex.getStatusCode().value(),//
          ex.getMessage(), //
          MethodUtil.jsonToObject(ex.getResponseBodyAsString(), errorType)//
      );
    }
  }

}
