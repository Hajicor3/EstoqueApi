package com.hajicor3.EstoqueApi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hajicor3.EstoqueApi.entities.Movimentacao;

import feign.Param;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {
	
	public Movimentacao findByIdProduto(Long idProduto);
	
	@Query("SELECT f FROM Movimentacao f WHERE f.idProduto = :idProduto")
	public List<Movimentacao> findAllByIdProduto(@Param("idPropduto") Long idProduto);
}
