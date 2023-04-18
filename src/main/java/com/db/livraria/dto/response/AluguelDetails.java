package com.db.livraria.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Builder
public class AluguelDetails {
    @JsonFormat(pattern = "dd/MM/yyyy hh:mm")
    private LocalDateTime dataRetirada;
    @JsonFormat(pattern = "dd/MM/yyyy hh:mm")
    private LocalDateTime dataDevolucao;
    private List<LivroDetails> livros;
    private LocatarioDetails locatario;
}
