package com.db.livraria.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Locatario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;
    @CPF(message = "CPF invalido")
    @Column(unique = true)
    private String cpf;
}
