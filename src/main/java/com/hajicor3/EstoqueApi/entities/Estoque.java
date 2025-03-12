package com.hajicor3.EstoqueApi.entities;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import com.hajicor3.EstoqueApi.entities.enums.TipoDeMovimentacao;

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
@FilterDef(name = "softDeleteFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "softDeleteFilter", condition = "deleted = :isDeleted")
public class Estoque {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long idProduto;
	private Long idFornecedor;
	private Long quantidade;
	private Boolean deleted = false;
	
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
	
	public void setQuantidade(TipoDeMovimentacao movimentacao) {
		
		if(movimentacao == TipoDeMovimentacao.ENTRADA) {
			this.quantidade++;
		}
		else {
			if(quantidade == 0) {
				throw new IllegalArgumentException("A quantidade em estoque não pode ser menor que zero!");
			}
			this.quantidade--;
		}
	}
	
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
}
