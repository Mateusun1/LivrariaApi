package com.db.livraria.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
@Getter
@Builder
public class LivroDetails {
    private String nome;
    private boolean alugado;
    private List<AutorDetails> autores;
}
