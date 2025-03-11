package com.hajicor3.EstoqueApi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hajicor3.EstoqueApi.entities.Estoque;
import com.hajicor3.EstoqueApi.entities.Movimentacao;
import com.hajicor3.EstoqueApi.entities.dtos.MovimentacaoRequest;
import com.hajicor3.EstoqueApi.entities.enums.TipoDeMovimentacao;
import com.hajicor3.EstoqueApi.repositories.EstoqueRepository;
import com.hajicor3.EstoqueApi.repositories.MovimentacaoRepository;

@Service
public class MovimentacaoService {
	
	@Autowired
	private MovimentacaoRepository movimentacaoRepository;
	@Autowired
	private EstoqueRepository estoqueRepository;
	
	public Movimentacao salvar(MovimentacaoRequest movimentacaoRequest) {
		
		var movimentacao = new Movimentacao(movimentacaoRequest.getIdProduto(),movimentacaoRequest.getTipoDeMovimentacao());
		var movimentacaoSalva = movimentacaoRepository.save(movimentacao);
		
		var  estoque = estoqueRepository.getReferenceById(movimentacaoSalva.getIdProduto());
		var tipoDeMovimentacao = movimentacaoSalva.getTipoDeMovimentacao();
		updateQuantidadeEstoque(estoque, tipoDeMovimentacao);
		estoqueRepository.save(estoque);
		
		return movimentacaoSalva;
	}
	
	public void updateQuantidadeEstoque(Estoque estoque, TipoDeMovimentacao movimentacao) {
		estoque.setQuantidade(movimentacao);
	}
	
}
