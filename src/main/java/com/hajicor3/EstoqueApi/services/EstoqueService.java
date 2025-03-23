package com.hajicor3.EstoqueApi.services;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.hajicor3.EstoqueApi.entities.Estoque;
import com.hajicor3.EstoqueApi.entities.dtos.EstoqueRequest;
import com.hajicor3.EstoqueApi.entities.dtos.EstoqueResponse;
import com.hajicor3.EstoqueApi.repositories.EstoqueRepository;
import com.hajicor3.EstoqueApi.services.exceptions.DataBaseException;
import com.hajicor3.EstoqueApi.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EstoqueService {

	private final EstoqueRepository estoqueRepository;
	
	@Transactional
	public Estoque salvar(EstoqueRequest estoque){
		
		var estoqueSalvo = new Estoque(estoque.getIdProduto(),estoque.getIdFornecedor());
		
		return estoqueRepository.save(estoqueSalvo);
	}
	
	@Transactional
	public List<EstoqueResponse> buscarTodos(){
		
		List<EstoqueResponse> lista = estoqueRepository.findAll().stream().map(x -> EstoqueResponse
				.builder()
				.id(x.getId())
				.idFornecedor(x.getIdFornecedor())
				.idProduto(x.getIdProduto())
				.quantidade(x.getQuantidade())
				.build())
				.toList();
		return lista;
	}
	
	@Transactional
	public List<EstoqueResponse> buscarTodosDeleteados(){
		List<EstoqueResponse> lista = estoqueRepository.findAllDeleted().stream().map(x -> EstoqueResponse
				.builder()
				.id(x.getId())
				.idFornecedor(x.getIdFornecedor())
				.idProduto(x.getIdProduto())
				.quantidade(x.getQuantidade())
				.build())
				.toList();
		return lista;
	}
	
	@Transactional
	public EstoqueResponse buscarPorId(Long id) {
		try {
			var estoque = estoqueRepository.getReferenceById(id);
			
			return EstoqueResponse
					.builder()
					.id(estoque.getId())
					.idProduto(estoque.getIdProduto())
					.idFornecedor(estoque.getIdFornecedor())
					.quantidade(estoque.getQuantidade())
					.build();
		}
		catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
		catch(NullPointerException e) {
			throw new ResourceNotFoundException(id);
		}
	}
	
	@Transactional
	public EstoqueResponse estoquePorIdProduto(Long id) {
		try {
			var estoque = estoqueRepository.findByIdProduto(id);
			return EstoqueResponse
					.builder()
					.id(estoque.getId())
					.idProduto(estoque.getIdProduto())
					.idFornecedor(estoque.getIdFornecedor())
					.quantidade(estoque.getQuantidade())
					.build();
		}
		catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
		catch(NullPointerException e) {
			throw new ResourceNotFoundException(id);
		}
	}
	
	@Transactional
	public List<EstoqueResponse> estoquesPorIdFornecedor(Long id) {
		try {
			var estoque = estoqueRepository.findByIdFornecedor(id);
			return estoque.stream().map(x -> EstoqueResponse
					.builder()
					.id(x.getId())
					.idFornecedor(x.getIdFornecedor())
					.idProduto(x.getIdProduto())
					.quantidade(x.getQuantidade())
					.build())
					.toList();
		}
		catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
		catch(NullPointerException e) {
			throw new ResourceNotFoundException(id);
		}
	}
	
	@Transactional
	public Long estoqueQuantidade(Long id) {
		try {
			return estoqueRepository.findByIdProduto(id).getQuantidade();
		}
		catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
		catch(NullPointerException e) {
			throw new ResourceNotFoundException(id);
		}
	}
	
	public Map<Long, Long> listaDeQuantidadePorProduto(){
		var estoque = estoqueRepository.findAll();
		
		return estoque.stream().collect(Collectors.toMap(
				x -> x.getIdProduto(),
				x -> x.getQuantidade()));
	}
	
	@Transactional
	public void deletarPorIdProduto(Long id) {
		try {
			var estoque = estoqueRepository.findByIdProduto(id);
			
			estoqueRepository.delete(estoque);
		}
		catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
		catch(NullPointerException e) {
			throw new ResourceNotFoundException(id);
		}
		catch(DataIntegrityViolationException e) {
			throw new DataBaseException(e.getMessage());
		}
	}
	
	@Transactional
	public void deletarPorIdFornecedor(Long id) {
		try {
			List<Estoque> estoques = estoqueRepository.findByIdFornecedor(id);
			estoques.forEach(x -> x.setDeleted(true));
			estoqueRepository.saveAll(estoques);
		}
		catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
		catch(NullPointerException e) {
			throw new ResourceNotFoundException(id);
		}
		catch(DataIntegrityViolationException e) {
			throw new DataBaseException(e.getMessage());
		}
	}
}


