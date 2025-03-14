package com.hajicor3.EstoqueApi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hajicor3.EstoqueApi.entities.Estoque;

import feign.Param;

public interface EstoqueRepository extends JpaRepository<Estoque, Long>{
	
	@Query("SELECT f FROM Estoque f WHERE f.idProduto = :idProduto AND f.deleted = false")
	public Estoque findByIdProduto(@Param("idProduto") Long idProduto);
	
	@Query("SELECT f FROM Estoque f WHERE f.idFornecedor = :idFornecedor AND f.deleted = false")
	public List<Estoque> findByIdFornecedor(@Param("idFornecedor") Long idFornecedor);
	
	@Query("SELECT f FROM Estoque f WHERE f.deleted = false")
	public List<Estoque> findAll();
	
	@Query("SELECT f FROM Estoque f WHERE f.deleted = true")
	public List<Estoque> findAllDeleted();
}
