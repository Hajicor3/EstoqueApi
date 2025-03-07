package com.hajicor3.EstoqueApi.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstoqueRequest {

	private Long idProduto;
	private Long idFornecedor;
	
}
