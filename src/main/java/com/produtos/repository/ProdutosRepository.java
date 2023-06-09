package com.produtos.repository;

import com.produtos.model.ProdutoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface ProdutosRepository extends JpaRepository<ProdutoModel, UUID> {
}
