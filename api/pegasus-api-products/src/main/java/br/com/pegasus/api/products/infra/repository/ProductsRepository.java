package br.com.pegasus.api.products.infra.repository;

import br.com.pegasus.api.products.infra.repository.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductsRepository extends JpaRepository<ProductEntity, Long> {
  Optional<ProductEntity> findByName(String name);
}
