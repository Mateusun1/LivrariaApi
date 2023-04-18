package com.db.livraria.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.time.Year;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 100, message = "Nome tem que ter entre 3 e 100 caracteres")
    private String nome;
    @NotNull(message = "Gênero não pode ser nulo")
    private String genero;
    @NotNull(message = "Ano de nascimento é obrigatório")
    private Year anoNascimento;
    @CPF(message = "CPF invalido")
    @Column(unique = true)
    private String cpf;

}
