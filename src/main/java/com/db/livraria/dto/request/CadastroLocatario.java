package com.db.livraria.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@Getter
@Builder
public class CadastroLocatario {
    @NotBlank(message = "Nome não pode ser vazio")
    @Size(min = 3, max = 100, message = "Nome tem que ter entre 3 e 100 caracteres")
    private String nome;
    @NotNull(message = "Gênero não pode nulo")
    private String genero;
    @NotBlank(message = "Número de telefone não pode ser vazio")
    @Size(min = 10, max = 11, message = "Número de telefone deve conter ddd")
    private String telefone;
    @Email(message = "email invalido")
    @Column(unique = true)
    private String email;
    private LocalDate dataNascimento;
    @CPF(message = "CPF invalido")
    @Column(unique = true)
    private String cpf;
}
