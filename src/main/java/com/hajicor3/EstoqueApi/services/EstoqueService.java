package com.hajicor3.EstoqueApi.services;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hajicor3.EstoqueApi.entities.Estoque;
import com.hajicor3.EstoqueApi.entities.dtos.EstoqueRequest;
import com.hajicor3.EstoqueApi.entities.dtos.EstoqueResponse;
import com.hajicor3.EstoqueApi.repositories.EstoqueRepository;

@Service
public class EstoqueService {

	@Autowired
	private EstoqueRepository estoqueRepository;
	
	
	public Estoque salvar(EstoqueRequest estoque){
		
		var estoqueSalvo = new Estoque(estoque.getIdProduto(),estoque.getIdFornecedor());
		
		return estoqueRepository.save(estoqueSalvo);
	}
	
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
	
	public EstoqueResponse buscarPorId(Long id) {
		
		var estoque = estoqueRepository.getReferenceById(id);
		
		return EstoqueResponse
				.builder()
				.id(estoque.getId())
				.idProduto(estoque.getIdProduto())
				.idFornecedor(estoque.getIdFornecedor())
				.quantidade(estoque.getQuantidade())
				.build();
	}
	
	public EstoqueResponse estoquePorIdProduto(Long id) {
		var estoque = estoqueRepository.findByIdProduto(id);
		return EstoqueResponse
				.builder()
				.id(estoque.getId())
				.idProduto(estoque.getIdProduto())
				.idFornecedor(estoque.getIdFornecedor())
				.quantidade(estoque.getQuantidade())
				.build();
	}
	
	public List<EstoqueResponse> estoquesPorIdFornecedor(Long id) {
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
	public Long estoqueQuantidade(Long id) {
		
		return estoqueRepository.findByIdProduto(id).getQuantidade();
	}
	
	public Map<Long, Long> listaDeQuantidadePorProduto(){
		var estoque = estoqueRepository.findAll();
		
		return estoque.stream().collect(Collectors.toMap(
				x -> x.getIdProduto(),
				x -> x.getQuantidade()));
	}
	
	public void deletarPorIdProduto(Long id) {
		
		var estoque = estoqueRepository.findByIdProduto(id);
		
		estoqueRepository.delete(estoque);
	}
	
	public void deletarPorIdFornecedor(Long id) {
		List<Estoque> estoques = estoqueRepository.findByIdFornecedor(id);
		estoques.forEach(x -> x.setDeleted(true));
		estoqueRepository.saveAll(estoques);
		
	
	}
}


