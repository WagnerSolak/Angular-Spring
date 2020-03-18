package com.example.solak.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.solak.api.model.Categoria;

//JpaRepository<Categoria, Long> -> métodos prontos para serem utilizados, e as implementações quem fornece é o SpringDataJpa

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{

}
