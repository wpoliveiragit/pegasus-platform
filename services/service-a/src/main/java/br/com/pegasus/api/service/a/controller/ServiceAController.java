package br.com.pegasus.api.service.a.controller;

import br.com.pegasus.api.service.a.model.PageableModel;
import br.com.pegasus.api.service.a.model.PaginationModel;
import br.com.pegasus.api.service.a.model.ProductModel;
import br.com.pegasus.api.service.a.model.ProductPageModel;
import br.com.pegasus.api.service.a.repository.impl.ProductsRepositoryImpl;
import br.com.pegasus.api.service.a.service.ProductsService;
import br.com.pegasus.api.service.a.type.PaginationResponseType;
import br.com.pegasus.api.service.a.type.ProductCreateRequestType;
import br.com.pegasus.api.service.a.repository.entity.ProductEntity;
import br.com.pegasus.api.service.a.type.ProductPageResponseType;
import br.com.pegasus.api.service.a.type.ProductResponseType;
import br.com.pegasus.api.service.a.type.ProductUpdateRequestType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Validated
@RestController
@RequestMapping("/products")
public class ServiceAController {

  private final AtomicLong idGen = new AtomicLong(1);
  private final List<ProductEntity> products;
  private final ProductsService productsService;

  public ServiceAController(ProductsService productsService) {
    this.productsService = productsService;
    this.products = IntStream.rangeClosed(1, 20)
        .mapToObj(i -> new ProductEntity(idGen.getAndIncrement(), "Item" + i, 3500f, 10))
        .collect(Collectors.toList());
  }

  @GetMapping("/hello")
  public String hello() {
    System.out.println("in service-a");
    return "Resposta Service-a: OK";
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProductResponseType> getOne(
      @PathVariable(value = "id") @Positive Long id) {

    return ResponseEntity.ok(toType(productsService.getOne(new ProductModel(id))));
  }

  @GetMapping
  public ResponseEntity<ProductPageResponseType> getAll(
      @RequestParam(value = "page", defaultValue = "0") @Min(0) Integer page,
      @RequestParam(value = "size", defaultValue = "0") @Min(1) Integer size) {

    return ResponseEntity.ok(toType(productsService.getAll(new PaginationModel(page, size))));
  }

  @PostMapping
  public ResponseEntity<ProductResponseType> create(
      @Valid @RequestBody ProductCreateRequestType body) {

    ProductModel outModel = productsService.create(//
        new ProductModel(//
            body.getName(),//
            body.getPrice(),//
            body.getQuantity()//
        )//
    );
    return ResponseEntity.status(HttpStatus.CREATED.value()).body(toType(outModel));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ProductResponseType> update(
      @PathVariable(value = "id") @Positive Long id,
      @Valid @RequestBody ProductUpdateRequestType body) {

    ProductModel outModel = productsService.update(//
        new ProductModel(//
            id,//
            body.getName(),//
            body.getPrice(),//
            body.getQuantity()//
        )//
    );
    return ResponseEntity.ok(toType(outModel));
  }

  @DeleteMapping("/{id}")
  public void delete(
      @RequestParam(value = "id") Long id) {

    productsService.delete(new ProductModel(id));
  }

  private ProductResponseType toType(ProductModel inModel) {
    return new ProductResponseType(//
        inModel.getId(),//
        inModel.getName(),//
        inModel.getPrice(),//
        inModel.getQuantity()//
    );
  }

  private ProductPageResponseType toType(ProductPageModel outModel) {
    PageableModel pagM = outModel.getPagination();
    var pagT = new PaginationResponseType(//
        pagM.getPage(),//
        pagM.getSize(),//
        pagM.getElements(),//
        pagM.getPages(),//
        pagM.getPrevious(),//
        pagM.getNext()//
    );
    List<ProductResponseType> elements = outModel.getProducts().stream().map(this::toType).toList();
    return new ProductPageResponseType(pagT, elements);
  }

}
