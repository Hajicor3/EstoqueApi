package com.hajicor3.EstoqueApi.entities;

import java.time.LocalDate;

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
	private LocalDate data;
	private Long idProduto;
	private TipoDeMovimentacao tipoDeMovimentacao;
	
}
