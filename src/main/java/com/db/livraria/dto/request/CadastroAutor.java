package com.db.livraria.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.br.CPF;

import java.time.Year;

@Getter
@Builder
public class CadastroAutor {
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 100, message = "Nome tem que ter entre 3 e 100 caracteres")
    private String nome;
    @NotNull(message = "Gênero não pode ser nulo")
    private String genero;
    @NotNull(message = "Ano de nascimento é obrigatório")
    private Year anoNascimento;
    @CPF(message = "CPF invalido")
    private String cpf;
}
