package com.hajicor3.EstoqueApi.entities;

import java.time.LocalDate;
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
public class movimentacao {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@JsonFormat(shape = Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate data;
	private Long idProduto;
	private TipoDeMovimentacao tipoDeMovimentacao;
	
	public movimentacao(LocalDate data, Long idProduto, TipoDeMovimentacao tipoDeMovimentacao) {
		this.data = LocalDate.now();
		this.idProduto = idProduto;
		this.tipoDeMovimentacao = tipoDeMovimentacao;
	}

	public void setIdProduto(Long idProduto) {
		if(idProduto == null || idProduto <= 0) {
			throw new IllegalArgumentException("O id do produto não pode ser nulo, menor ou igual a zero!");
		}
		this.idProduto = idProduto;
	}

	public void setTipoDeMovimentacao(TipoDeMovimentacao tipoDeMovimentacao) {
		Objects.requireNonNull(tipoDeMovimentacao, "O tipo de movimentação não pode ser nulo!");
		this.tipoDeMovimentacao = tipoDeMovimentacao;
	}
	
	
	
}
