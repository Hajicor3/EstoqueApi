package com.hajicor3.EstoqueApi.entities.dtos;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.hajicor3.EstoqueApi.entities.enums.TipoDeMovimentacao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovimentacaoResponse {

	private Long id;
	@JsonFormat(shape = Shape.STRING, pattern = "dd-MM-yyyy'T'HH:mm:ss'Z'",timezone = "GMT")
	private Instant data;
	private Long idProduto;
	private TipoDeMovimentacao tipoDeMovimentacao;
}
