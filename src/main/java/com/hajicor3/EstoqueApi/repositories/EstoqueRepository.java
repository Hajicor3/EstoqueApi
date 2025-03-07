package com.hajicor3.EstoqueApi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hajicor3.EstoqueApi.entities.Estoque;

public interface EstoqueRepository extends JpaRepository<Estoque, Long>{

}
