package com.hajicor3.EstoqueApi.entities.dtos;

import java.time.LocalDate;

import com.hajicor3.EstoqueApi.entities.enums.TipoDeMovimentacao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovimentacaoRequest {
	
	private Long idProduto;
	private LocalDate data;
	private TipoDeMovimentacao tipoDeMovimentacao;
}
