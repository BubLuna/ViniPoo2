package com.example.pooVini.dto;

import com.example.pooVini.model.Livro;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LivroResponse {

    private Long id;
    private String titulo;
    private String autor;
    private String codigo;
    private Integer anoPublicacao;
    private Double preco;

    public static LivroResponse from(Livro livro) {
        return new LivroResponse(
                livro.getId(),
                livro.getTitulo(),
                livro.getAutor(),
                livro.getCodigo(),
                livro.getAnoPublicacao(),
                livro.getPreco()
        );
    }
}
