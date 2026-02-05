package br.com.pegasus.portal.app.test;

import br.com.pegasus.portal.app.type.ProductPageResponseType;
import br.com.pegasus.portal.app.type.ProductRequestType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class RequestService {

  private static final String SERVICE_A_API = "http://gateway:8080/service-a";
  private final RestTemplate restTemplate = new RestTemplate();

  public void findAllPage(int page, int size) {
    HttpHeaders headers = new HttpHeaders();
    HttpEntity<Void> entity = new HttpEntity<>(headers);

    UriComponentsBuilder uri =
        UriComponentsBuilder
            .fromHttpUrl(SERVICE_A_API)
            .queryParam("page", page)
            .queryParam("size", size);

    ResponseEntity<ProductPageResponseType> response =
        restTemplate.exchange(
            uri.toUriString(),
            HttpMethod.GET,
            entity,
            ProductPageResponseType.class
        );

    System.out.println(response.getBody().getProducts().size());
  }

  public void findById(long id) {
    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "Bearer token-xyz");
    headers.set("X-Request-Id", "123");

    HttpEntity<Void> entity = new HttpEntity<>(headers);

    String url = "http://gateway:8080/service-a/{id}";

    ResponseEntity<ProductRequestType> response =
        restTemplate.exchange(
            url,
            HttpMethod.GET,
            entity,
            ProductRequestType.class,
            id
        );


    System.out.println(response.getBody().getName());
  }

  public void create(String name, Float price, int quantity) {
    HttpHeaders headers = new HttpHeaders();

    ProductRequestType body = new ProductRequestType("Notebook", 3500F, 10);

    HttpEntity<ProductRequestType> entity = new HttpEntity<>(body, headers);

    ResponseEntity<ProductRequestType> response =
        restTemplate.postForEntity(
            "http://gateway:8080/service-a/create",
            entity,
            ProductRequestType.class
        );

    System.out.println(response.getBody().getName());
  }

  public void update(Long id, String name, Float price, int quantity) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    ProductRequestType body =
        new ProductRequestType(name, price, quantity);

    HttpEntity<ProductRequestType> entity =
        new HttpEntity<>(body, headers);

    ResponseEntity<Void> response =
        restTemplate.exchange(
            "http://gateway:8080/service-a/update/" + id,
            HttpMethod.PUT,
            entity,
            Void.class,
            5L
        );
  }

  public void delete() {
    Object ret = restTemplate.getForObject("http://gateway:8080/service-a", String.class);
    System.out.println(restTemplate.getForObject("http://gateway:8080/service-a/hello", String.class));
  }
}
