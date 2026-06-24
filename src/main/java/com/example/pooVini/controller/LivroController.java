package com.example.pooVini.controller;

import com.example.pooVini.dto.LivroRequest;
import com.example.pooVini.dto.LivroResponse;
import com.example.pooVini.exception.CodigoDuplicadoException;
import com.example.pooVini.exception.RecursoNaoEncontradoException;
import com.example.pooVini.model.Livro;
import com.example.pooVini.repository.LivroRepository;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/livros")
public class LivroController {

    private final LivroRepository livroRepository;

    public LivroController(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    @PostMapping
    public ResponseEntity<LivroResponse> cadastrar(@Valid @RequestBody LivroRequest request) {
        if (livroRepository.existsByCodigo(request.getCodigo())) {
            throw new CodigoDuplicadoException("Já existe um livro com este código cabeça.");
        }
        Livro livro = new Livro();
        livro.setTitulo(request.getTitulo());
        livro.setAutor(request.getAutor());
        livro.setCodigo(request.getCodigo());
        livro.setAnoPublicacao(request.getAnoPublicacao());
        livro.setPreco(request.getPreco());
        Livro salvo = livroRepository.save(livro);
        return ResponseEntity.status(HttpStatus.CREATED).body(LivroResponse.from(salvo));
    }

    @GetMapping
    public ResponseEntity<List<LivroResponse>> listar() {
        List<LivroResponse> livros = livroRepository.findAll().stream()
                .map(LivroResponse::from)
                .toList();
        return ResponseEntity.ok(livros);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivroResponse> buscarPorId(@PathVariable Long id) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Livro não encontrado, ta querendo 'As longas Madeixas de um Careca'? Infelizmente eu pesquisei, esse livro nao existe na vida real :( "));
        return ResponseEntity.ok(LivroResponse.from(livro));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LivroResponse> atualizar(@PathVariable Long id, @Valid @RequestBody LivroRequest request) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Livro não encontrado, ta tentando atualizar 'As longas Madeixas de um Careca'? Infelizmente eu pesquisei, esse livro nao existe na vida real :( ?"));

        if (!livro.getCodigo().equals(request.getCodigo()) && livroRepository.existsByCodigo(request.getCodigo())) {
            throw new CodigoDuplicadoException("Já existe um livro com este código, tenta depois de novo rsrs");
        }
        livro.setTitulo(request.getTitulo());
        livro.setAutor(request.getAutor());
        livro.setCodigo(request.getCodigo());
        livro.setAnoPublicacao(request.getAnoPublicacao());
        livro.setPreco(request.getPreco());
        Livro atualizado = livroRepository.save(livro);
        return ResponseEntity.ok(LivroResponse.from(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        if (!livroRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Livro não encontrado, ta querendo deletar o inexistente?");
        }
        livroRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
