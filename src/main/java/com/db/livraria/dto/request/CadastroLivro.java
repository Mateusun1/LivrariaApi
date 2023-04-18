package com.db.livraria.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.ISBN;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class CadastroLivro {
    @NotBlank(message = "Nome não pode ser vazio")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    private String nome;
    @ISBN(message = "ISBN invalido")
    private String isbn;
    @NotNull(message = "Data de Publicação não pode ser nula")
    private LocalDate dataPublicacao;
    @NotNull(message = "Autores não podem ser nulos")
    private List<Long> autoresId;
}
