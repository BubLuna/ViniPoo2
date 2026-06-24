package com.example.pooVini.repository;

import com.example.pooVini.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    boolean existsByCodigo(String codigo);
}
