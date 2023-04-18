package com.db.livraria.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
@Getter
@Builder
public class CadastroAluguel {
    @NotNull(message = "Livros não podem ser nuloa")
    private List<Long> idLivros;
    @NotNull(message = "Locatario não pode ser nulo")
    private Long idLocatario;
}
