package com.hajicor3.EstoqueApi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hajicor3.EstoqueApi.entities.Movimentacao;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {
	
	public Movimentacao findByIdProduto(Long id);
}
