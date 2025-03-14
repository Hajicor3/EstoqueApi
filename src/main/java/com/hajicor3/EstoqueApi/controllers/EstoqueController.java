package com.hajicor3.EstoqueApi.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping(value = "/estoques")
public class EstoqueController {
	
	@Autowired
	private EstoqueService estoqueService;

	@Operation(description = "Salva um Estoque no banco de dados.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Salva um Estoque no banco de dados."),
			@ApiResponse(responseCode = "400", description = "Parametros inválidos."),
	})
	@PostMapping
	public Estoque salvarEstoque(@RequestBody EstoqueRequest estoque) {
		return estoqueService.salvar(estoque);
	}
	
	@Operation(description = "Retorna uma lista de Dto´s de todos os estoques do banco.")
	@ApiResponses(value = @ApiResponse(responseCode = "200",description = "Retorna uma lista de todos os estoques."))
	@GetMapping
	public ResponseEntity<List<EstoqueResponse>> buscarListaDeEstoques(){
		List<EstoqueResponse> lista = estoqueService.buscarTodos();
		return ResponseEntity.ok().body(lista);
	}
	
	@Operation(description = "Retorna uma lista de Dto´s de todos os estoques deletados.")
	@ApiResponses(value = @ApiResponse(responseCode = "200",description = "Retorna uma lista de todos os estoques."))
	@GetMapping(value = "/deletados")
	public ResponseEntity<List<EstoqueResponse>> buscarListaDeEstoquesDeletados(){
		List<EstoqueResponse> lista = estoqueService.buscarTodosDeleteados();
		return ResponseEntity.ok().body(lista);
	}
	
	@Operation(description = "Resgata um estoque do banco de dados pelo id.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Retorna um estoque."),
			@ApiResponse(responseCode = "404", description = "Não existe estoque no id informado.")
	})
	@GetMapping(value = "/{id}")
	public ResponseEntity<EstoqueResponse> buscarEstoquePorId(@PathVariable Long id){
		var estoque = estoqueService.buscarPorId(id);
		return ResponseEntity.ok().body(estoque);
	}
	
	@Operation(description = "Resgata um estoque do banco de dados pelo id do produto.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Retorna um estoque."),
			@ApiResponse(responseCode = "404", description = "Não existe estoque no id informado.")
	})
	@GetMapping(params = "id")
	public ResponseEntity<EstoqueResponse> encontrarEstoquePeloIdProduto(@RequestParam Long id) {
		var estoque = estoqueService.estoquePorIdProduto(id);
		return ResponseEntity.ok().body(estoque);
	}
	
	@Operation(description = "Retorna uma lista de Dto´s de todos os estoques pelo id do fornecedor.")
	@ApiResponses(value = @ApiResponse(responseCode = "200",description = "Retorna uma lista de todos os estoques."))
	@GetMapping(path = "/fornecedor/{id}")
	public ResponseEntity<List<EstoqueResponse>> econtrarEstoquesPorIdFornecedor(@PathVariable Long id){
		var estoques = estoqueService.estoquesPorIdFornecedor(id);
		
		return ResponseEntity.ok().body(estoques);
	}
	
	@Operation(description = "Resgata a quantidade de um produto em estoque do banco de dados pelo id do produto.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Retorna a quantidade do produto em estoque."),
			@ApiResponse(responseCode = "404", description = "Não existe estoque no id informado.")
	})
	@GetMapping(value = "/qntd/produto/{id}")
	public ResponseEntity<Long> quantidadeEmEstoquePorIdProduto(@PathVariable Long id){
		
		var quantidade = estoqueService.estoqueQuantidade(id);
		return ResponseEntity.ok().body(quantidade);
	}
	
	@Operation(description = "Resgata uma map de todas as quantidades de produtos em estoques do banco de dados pelo id do produto.")
	@ApiResponse(responseCode = "200", description = "Retorna todas as quantidades de produtos em estoque.")
	@GetMapping(value = "/qntd/produto/list")
	public ResponseEntity<Map<Long,Long>> produtoQuantidadeLista(){
		var lista = estoqueService.listaDeQuantidadePorProduto();
		return ResponseEntity.ok().body(lista);
	}
	
	@Operation(description = "Deleta um estoque do banco de dados pelo id.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Efetua a exclusão de um estoque."),
			@ApiResponse(responseCode = "404", description = "Não existe estoque no id informado."),
			@ApiResponse(responseCode = "400", description = "Parametros inválidos.")
	})
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deletarEstoquePorIdProduto(@PathVariable Long id){
		estoqueService.deletarPorIdProduto(id);
		return ResponseEntity.noContent().build();
	}
	
	@Operation(description = "Realiza um soft delete de todos os estoques de um fornecedor pelo id do fornecedor.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Efetua a exclusão de um estoque."),
			@ApiResponse(responseCode = "404", description = "Não existe estoque no id informado."),
			@ApiResponse(responseCode = "400", description = "Parametros inválidos.")
	})
	@DeleteMapping(value = "/fornecedor/{id}")
	public ResponseEntity<Void> deletarEstoquesDeFornecedor(@PathVariable Long id){
		estoqueService.deletarPorIdFornecedor(id);
		return ResponseEntity.noContent().build();
	}
}
