package com.hajicor3.EstoqueApi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hajicor3.EstoqueApi.entities.Estoque;
import com.hajicor3.EstoqueApi.entities.dtos.EstoqueRequest;
import com.hajicor3.EstoqueApi.entities.dtos.EstoqueResponse;
import com.hajicor3.EstoqueApi.services.EstoqueService;

@RestController
@RequestMapping(value = "/estoques")
public class EstoqueController {
	
	@Autowired
	private EstoqueService estoqueService;
	
	@PostMapping
	public Estoque salvarEstoque(@RequestBody EstoqueRequest estoque) {
		return estoqueService.salvar(estoque);
		
	}
	
	@GetMapping
	public ResponseEntity<List<EstoqueResponse>> buscarListaDeEstoques(){
		List<EstoqueResponse> lista = estoqueService.buscarTodos();
		
		return ResponseEntity.ok().body(lista);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<EstoqueResponse> buscarEstoquePorId(@PathVariable Long id){
		var estoque = estoqueService.buscarPorId(id);
		
		return ResponseEntity.ok().body(estoque);
	}
	
	@GetMapping(params = "id")
	public ResponseEntity<EstoqueResponse> encontrarPeloIdProduto(@RequestParam Long id) {
		
		var estoque = estoqueService.EstoquePorIdProduto(id);
		
		return ResponseEntity.ok().body(estoque);
	}
}
