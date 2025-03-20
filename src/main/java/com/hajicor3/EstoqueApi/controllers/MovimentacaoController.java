package com.hajicor3.EstoqueApi.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.hajicor3.EstoqueApi.entities.Movimentacao;
import com.hajicor3.EstoqueApi.entities.dtos.MovimentacaoRequest;
import com.hajicor3.EstoqueApi.entities.dtos.MovimentacaoResponse;
import com.hajicor3.EstoqueApi.services.MovimentacaoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping(value = "/movimentacoes")
public class MovimentacaoController {
	
	@Autowired
	private MovimentacaoService movimentacaoService;
	
	@Operation(description = "Salva uma movimentação no banco de dados.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Salva uma movimentação no banco de dados."),
			@ApiResponse(responseCode = "400", description = "Parametros inválidos."),
			@ApiResponse(responseCode = "404", description = "O estoque do id não existe.")
	})
	@PostMapping
	public ResponseEntity<Movimentacao> salvarMovimentacao(@RequestBody MovimentacaoRequest movimentacaoRequest) {
		
		var movimentacao = movimentacaoService.salvar(movimentacaoRequest);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(movimentacao.getId()).toUri();
		return ResponseEntity.created(uri).body(movimentacao);
	}

	@Operation(description = "Retorna uma lista de Dto´s de todas as movimentações do banco.")
	@ApiResponses(value = @ApiResponse(responseCode = "200",description = "Retorna uma lista de todas as movimentações."))
	@GetMapping
	public ResponseEntity<List<MovimentacaoResponse>> resgatarMovimentacoes(){
		var movLista = movimentacaoService.resgatarTodas();
		return ResponseEntity.ok().body(movLista);
	}
	
	@Operation(description = "Retorna uma lista de Dto´s de todas as movimentações canceladas.")
	@ApiResponses(value = @ApiResponse(responseCode = "200",description = "Retorna uma lista de todas as movimentações."))
	@GetMapping(value = "/canceladas")
	public ResponseEntity<List<MovimentacaoResponse>> resgatarMovimentacoesCanceladas(){
		var movLista = movimentacaoService.resgatarTodasCanceladas();
		return ResponseEntity.ok().body(movLista);
	}
	
	@Operation(description = "Retorna uma lista de Dto´s de todas as movimentações de um produto.")
	@ApiResponses(value = @ApiResponse(responseCode = "200",description = "Retorna uma lista de todas as movimentações."))
	@GetMapping(value = "/produto/list/{id}")
	public ResponseEntity<List<MovimentacaoResponse>> resgatarMovimentacoesPorIdProduto(@PathVariable Long id){
		var movLista = movimentacaoService.resgatarTodasPorIdProduto(id);
		return ResponseEntity.ok().body(movLista);
	}
	
	@Operation(description = "Resgata uma movimentação do banco de dados pelo id.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Retorna uma movimentação."),
			@ApiResponse(responseCode = "404", description = "Não existe movimentação no id informado.")
	})
	@GetMapping(value = "/{id}")
	public ResponseEntity<MovimentacaoResponse> buscarMovimentacaoPorId(@PathVariable Long id){
		var mov = movimentacaoService.buscar(id);
		return ResponseEntity.ok().body(mov);
	}
	
	@Operation(description = "Deleta uma movimentação do banco de dados pelo id.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Efetua a exclusão da movimentação."),
			@ApiResponse(responseCode = "404", description = "Não existe movimentação no id informado."),
			@ApiResponse(responseCode = "400", description = "Parametros inválidos.")
	})
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deletarMovimentacaoPorId(@PathVariable Long id){
		movimentacaoService.deletar(id);
		return ResponseEntity.noContent().build();
	}
}
