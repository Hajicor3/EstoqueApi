package com.hajicor3.EstoqueApi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hajicor3.EstoqueApi.entities.Movimentacao;

import feign.Param;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {
	
	@Query("SELECT f FROM Movimentacao f WHERE f.idProduto = :idProduto AND f.cancelada = false")
	public List<Movimentacao> findAllByIdProduto(@Param("idPropduto") Long idProduto);
	
	@Query("SELECT f FROM Movimentacao f WHERE f.id = :id AND f.cancelada = false")
	public Movimentacao getReferenceById(Long id);
	
	@Query("SELECT f FROM Movimentacao f WHERE f.cancelada = false")
	public List<Movimentacao> findAll();
	
	@Query("SELECT f FROM Movimentacao f WHERE f.idProduto = :idProduto AND f.cancelada = true")
	public Movimentacao findCanceladaByIdProduto(@Param("idProduto") Long idProduto);
	
	@Query("SELECT f FROM Movimentacao f WHERE f.cancelada = true")
	public List<Movimentacao> findAllCancelada();
}
