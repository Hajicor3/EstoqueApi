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
			var movimentacao = new Movimentacao(movimentacaoRequest.getIdProduto(), movimentacaoRequest.getQuantidade(),movimentacaoRequest.getTipoDeMovimentacao());
			var movimentacaoSalva = movimentacaoRepository.save(movimentacao);
			
			var  estoque = estoqueRepository.findByIdProduto(movimentacaoSalva.getIdProduto());
			updateQuantidadeEstoque(estoque, movimentacaoSalva);
			estoqueRepository.save(estoque);
			
			return movimentacaoSalva;
		}
		catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException(movimentacaoRequest.getIdProduto());
		}
		catch(NullPointerException e) {
			throw new ResourceNotFoundException("O estoque o qual a movimentação foi destinada está vazio.");
		}
	}
	
	@Transactional
	public void updateQuantidadeEstoque(Estoque estoque, Movimentacao movimentacao) {
		estoque.setQuantidade(movimentacao.getTipoDeMovimentacao(), movimentacao.getQuantidade());
	}
	
	@Transactional
	public void cancelarMovimentacao(Long id) {
		try {
			var mov = movimentacaoRepository.getReferenceById(id);
			var estoque = estoqueRepository.findByIdProduto(mov.getIdProduto());
			Long qntd = mov.getQuantidade();
			
			if(mov.getTipoDeMovimentacao() == TipoDeMovimentacao.ENTRADA) {
				estoque.setQuantidade(TipoDeMovimentacao.SAIDA, qntd);
			}
			else {
				estoque.setQuantidade(TipoDeMovimentacao.ENTRADA, qntd);
			}
			
			mov.setCancelada(true);
			estoqueRepository.save(estoque);
			movimentacaoRepository.save(mov);
		}
		catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
		catch(NullPointerException e) {
			throw new ResourceNotFoundException(id);
		}
	}
	
	@Transactional
	public List<MovimentacaoResponse> resgatarTodas(){
		return movimentacaoRepository.findAll().stream().map(x -> MovimentacaoResponse
				.builder()
				.id(x.getId())
				.idProduto(x.getIdProduto())
				.tipoDeMovimentacao(x.getTipoDeMovimentacao())
				.quantidade(x.getQuantidade())
				.data(x.getData())
				.build())
				.toList();
	}
	
	@Transactional
	public List<MovimentacaoResponse> resgatarTodasPorIdProduto(Long id){
		try {
			return movimentacaoRepository.findAllByIdProduto(id).stream().map(x -> MovimentacaoResponse
					.builder()
					.id(x.getId())
					.idProduto(x.getIdProduto())
					.tipoDeMovimentacao(x.getTipoDeMovimentacao())
					.quantidade(x.getQuantidade())
					.data(x.getData())
					.build())
					.toList();
		}
		catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}
	
	@Transactional
	public List<MovimentacaoResponse> resgatarTodasCanceladas(){
		
		return movimentacaoRepository.findAllCancelada().stream().map(x -> MovimentacaoResponse
				.builder()
				.id(x.getId())
				.idProduto(x.getIdProduto())
				.tipoDeMovimentacao(x.getTipoDeMovimentacao())
				.quantidade(x.getQuantidade())
				.data(x.getData())
				.build())
				.toList();
	}
	
	@Transactional
	public MovimentacaoResponse buscar(Long id) {
		
		try {
			var mov = movimentacaoRepository.findByIdNotCancelled(id);
			return MovimentacaoResponse
					.builder()
					.id(mov.getId())
					.idProduto(mov.getIdProduto())
					.tipoDeMovimentacao(mov.getTipoDeMovimentacao())
					.quantidade(mov.getQuantidade())
					.data(mov.getData())
					.build();
		}
		catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}
	
	@Transactional
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
}
