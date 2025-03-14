package com.hajicor3.EstoqueApi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.hajicor3.EstoqueApi.entities.Estoque;
import com.hajicor3.EstoqueApi.entities.Movimentacao;
import com.hajicor3.EstoqueApi.entities.dtos.MovimentacaoRequest;
import com.hajicor3.EstoqueApi.entities.dtos.MovimentacaoResponse;
import com.hajicor3.EstoqueApi.entities.enums.TipoDeMovimentacao;
import com.hajicor3.EstoqueApi.repositories.EstoqueRepository;
import com.hajicor3.EstoqueApi.repositories.MovimentacaoRepository;
import com.hajicor3.EstoqueApi.services.exceptions.DataBaseException;
import com.hajicor3.EstoqueApi.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class MovimentacaoService {
	
	@Autowired
	private MovimentacaoRepository movimentacaoRepository;
	@Autowired
	private EstoqueRepository estoqueRepository;
	
	@Transactional
	public Movimentacao salvar(MovimentacaoRequest movimentacaoRequest) {
		try {
		var movimentacao = new Movimentacao(movimentacaoRequest.getIdProduto(),movimentacaoRequest.getTipoDeMovimentacao());
		var movimentacaoSalva = movimentacaoRepository.save(movimentacao);
		
		var  estoque = estoqueRepository.getReferenceById(movimentacaoSalva.getIdProduto());
		var tipoDeMovimentacao = movimentacaoSalva.getTipoDeMovimentacao();
		updateQuantidadeEstoque(estoque, tipoDeMovimentacao);
		estoqueRepository.save(estoque);
		
		return movimentacaoSalva;
		}
		catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException(movimentacaoRequest.getIdProduto());
		}
	}
	
	@Transactional
	public void updateQuantidadeEstoque(Estoque estoque, TipoDeMovimentacao movimentacao) {
		estoque.setQuantidade(movimentacao);
	}
	
	@Transactional
	public void cancelarMovimentacao(Long id) {
		var mov = movimentacaoRepository.getReferenceById(id);
		var estoque = estoqueRepository.findByIdProduto(mov.getIdProduto());
		
		if(mov.getTipoDeMovimentacao() == TipoDeMovimentacao.ENTRADA) {
			estoque.setQuantidade(TipoDeMovimentacao.SAIDA);
		}
		else {
			estoque.setQuantidade(TipoDeMovimentacao.ENTRADA);
		}
		
		mov.setCancelada(true);
		estoqueRepository.save(estoque);
		movimentacaoRepository.save(mov);
	}
	
	public List<MovimentacaoResponse> resgatarTodas(){
		return movimentacaoRepository.findAll().stream().map(x -> MovimentacaoResponse
				.builder()
				.id(x.getId())
				.idProduto(x.getIdProduto())
				.tipoDeMovimentacao(x.getTipoDeMovimentacao())
				.data(x.getData())
				.build())
				.toList();
	}
	
	public List<MovimentacaoResponse> resgatarTodasPorIdProduto(Long id){
		try {
		return movimentacaoRepository.findAllByIdProduto(id).stream().map(x -> MovimentacaoResponse
				.builder()
				.id(x.getId())
				.idProduto(x.getIdProduto())
				.tipoDeMovimentacao(x.getTipoDeMovimentacao())
				.data(x.getData())
				.build())
				.toList();
		}
		catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}
	
	public MovimentacaoResponse buscar(Long id) {
		
		try {
		var mov = movimentacaoRepository.getReferenceById(id);
		return MovimentacaoResponse
				.builder()
				.id(mov.getId())
				.idProduto(mov.getIdProduto())
				.tipoDeMovimentacao(mov.getTipoDeMovimentacao())
				.data(mov.getData())
				.build();
		}
		catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}
	
	public MovimentacaoResponse buscarPorIdProduto(Long id) {
		try {
		var mov = movimentacaoRepository.findByIdProduto(id);
		return MovimentacaoResponse
				.builder()
				.id(mov.getId())
				.idProduto(mov.getIdProduto())
				.tipoDeMovimentacao(mov.getTipoDeMovimentacao())
				.data(mov.getData())
				.build();
		}
		catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}
	
	public void deletar(Long id){
		try {
		if(!movimentacaoRepository.existsById(id)) {
			throw new ResourceNotFoundException(id);
		}
		movimentacaoRepository.deleteById(id);
		}
		catch(DataIntegrityViolationException e) {
			throw new DataBaseException(e.getMessage());
		}
	}
	
	public void deletarPorIdProduto(Long id){
		try {
		if(!movimentacaoRepository.existsById(id)) {
			throw new ResourceNotFoundException(id);
		}
		var mov = movimentacaoRepository.findByIdProduto(id);
		movimentacaoRepository.delete(mov);
		}
		catch(DataIntegrityViolationException e) {
			throw new DataBaseException(e.getMessage());
		}
	}
}
