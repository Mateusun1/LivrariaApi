package com.db.livraria.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.Year;
@Getter
@Builder
public class AutorDetails {
    private String nome;
    private String genero;
    private Year anoNascimento;
}
