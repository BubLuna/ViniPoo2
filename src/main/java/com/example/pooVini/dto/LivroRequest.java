package com.example.pooVini.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LivroRequest {

    @NotBlank(message = "O título é obrigatório né meu, ja viu livro sem titulo?.")
    private String titulo;

    @NotBlank(message = "O autor é obrigatório. Ou foi as vozes do além?")
    private String autor;

    @NotBlank(message = "Vou achar esse livro como? O código é obrigatório.")
    private String codigo;

    @NotNull(message = "Ou tu ainda nem publicou o livro, ou tu ta esquecendo de algo. Porque tu sabe que o ano de publicação é obrigatório né")
    @Min(value = 1000, message = "O ano de publicação deve ser no mínimo 1000, nem a biblia é tao antiga")
    @Max(value = 2100, message = "Tais no futuro e eu nao to sabendo? O ano de publicação deve ser no máximo 2100.")
    private Integer anoPublicacao;

    @NotNull(message = "O preço é obrigatório (a nao ser que tu queira dar tudo assim facil)")
    @Positive(message = "O preço deve ser maior que zero. Ou tu ta querendo dar tudo assim facil?")
    private Double preco;
}
