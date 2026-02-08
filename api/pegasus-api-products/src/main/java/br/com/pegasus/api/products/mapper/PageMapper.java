package br.com.pegasus.api.products.mapper;

import br.com.pegasus.api.products.model.PageableModel;
import br.com.pegasus.api.products.model.ProductPageModel;
import br.com.pegasus.api.products.repository.entity.ProductEntity;
import org.springframework.data.domain.Page;

public final  class PageMapper {

  public static ProductPageModel toModel(Page<ProductEntity> pageE) {
    return new ProductPageModel(//
        new PageableModel(//
            pageE.getNumber(), // página atual (0-based)
            pageE.getSize(), // tamanho da página
            pageE.getTotalElements(), // total de registros
            pageE.getTotalPages(), // total de páginas
            pageE.hasPrevious(), // existe anterior
            pageE.hasNext() // existe próxima
        ),//
        pageE.stream().map(ProductMapper::toModel).toList()//
    );
  }

}
