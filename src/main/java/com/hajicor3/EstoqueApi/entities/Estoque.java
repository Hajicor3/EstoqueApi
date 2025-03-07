package com.hajicor3.EstoqueApi.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_estoque")
@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class Estoque {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Long idProduto;
	private Long idFornecedor;
	private Long quantidade;
	
	public Estoque(Long idProduto, Long idFornecedor) {
		setIdProduto(idProduto);
		setIdFornecedor(idFornecedor);
		this.quantidade = 0L;
	}
	
	public void setIdProduto(Long idProduto) {
		
		if(idProduto <= 0 || idProduto == null) {
			throw new IllegalArgumentException("O id do produto não pode ser nulo, menor que zero ou igual a zero!");
		}
		
		this.idProduto = idProduto;
	}
	
	public void setIdFornecedor(Long idFornecedor) {
		
		if(idFornecedor <= 0 || idFornecedor == null) {
			throw new IllegalArgumentException("O id do fornecedor não pode ser nulo, menor que zero ou igual a zero!");
		}
		
		this.idFornecedor = idFornecedor;
	}
	
	public void setQuantidade(Long quantidade) {
		
		if(quantidade < 0 || quantidade == null) {
			throw new IllegalArgumentException("A quantodade não pode ser nula ou negativa!");
		}
	}
}
