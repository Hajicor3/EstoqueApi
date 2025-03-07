package com.hajicor3.EstoqueApi.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EstoqueResponse {
	
	private Long id;
	private Long idProduto;
	private Long idFornecedor;
	private Long quantidade;
}
