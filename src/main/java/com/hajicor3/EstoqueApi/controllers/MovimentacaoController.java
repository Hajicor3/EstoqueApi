package com.hajicor3.EstoqueApi.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.hajicor3.EstoqueApi.entities.Movimentacao;
import com.hajicor3.EstoqueApi.entities.dtos.MovimentacaoRequest;
import com.hajicor3.EstoqueApi.services.MovimentacaoService;

@RestController
@RequestMapping(value = "/movimentacoes")
public class MovimentacaoController {
	
	@Autowired
	private MovimentacaoService movimentacaoService;
	
	@PostMapping
	public ResponseEntity<Movimentacao> salvarMovimentacao(@RequestBody MovimentacaoRequest movimentacaoRequest) {
		
		var movimentacao = movimentacaoService.salvar(movimentacaoRequest);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(movimentacao.getId()).toUri();
		return ResponseEntity.created(uri).body(movimentacao);
	}
}
