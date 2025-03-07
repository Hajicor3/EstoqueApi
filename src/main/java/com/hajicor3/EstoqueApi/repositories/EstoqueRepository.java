package com.hajicor3.EstoqueApi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hajicor3.EstoqueApi.entities.Estoque;

public interface EstoqueRepository extends JpaRepository<Estoque, Long>{
	
	public Estoque findByIdProduto(Long id);
	
	public List<Estoque> findByIdFornecedor(Long id);
}
