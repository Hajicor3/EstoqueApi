package com.hajicor3.EstoqueApi.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class QuantidadeProdutoResponse {

	private Long idProduto;
	private Long quantidade;
}
