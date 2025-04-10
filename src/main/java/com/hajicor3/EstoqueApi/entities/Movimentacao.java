package com.hajicor3.EstoqueApi.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
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
@Table(name = "tb_movimentacao")
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class Movimentacao implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@JsonFormat(shape = Shape.STRING, pattern = "dd-MM-yyyy'T'HH:mm:ss'Z'",timezone = "GMT")
	private Instant data;
	private Long idProduto;
	private TipoDeMovimentacao tipoDeMovimentacao;
	private Long quantidade;
	private Boolean cancelada = false;
	
	public Movimentacao(Long idProduto, Long quantidade, TipoDeMovimentacao tipoDeMovimentacao) {
		this.data = Instant.now();
		setIdProduto(idProduto);
		setQuantidade(quantidade);
		setTipoDeMovimentacao(tipoDeMovimentacao);;
	}

	public void setIdProduto(Long idProduto) {
		if(idProduto == null || idProduto <= 0) {
			throw new IllegalArgumentException("O id do produto não pode ser nulo, menor ou igual a zero!");
		}
		this.idProduto = idProduto;
	}

	public void setQuantidade(Long quantidade) {
		if(quantidade == null || quantidade <= 0) {
			throw new IllegalArgumentException("A quantidade do produto não pode ser nula ou menor que zero!");
		}
		this.quantidade = quantidade;
	}
	
	public void setTipoDeMovimentacao(TipoDeMovimentacao tipoDeMovimentacao) {
		Objects.requireNonNull(tipoDeMovimentacao, "O tipo de movimentação não pode ser nulo!");
		this.tipoDeMovimentacao = tipoDeMovimentacao;
	}
	
	public void setCancelada(Boolean cancelada) {
		Objects.requireNonNull(cancelada, "O valor não pode ser nulo");
		this.cancelada = cancelada;
	}
}