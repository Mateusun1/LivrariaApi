package com.db.livraria.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Aluguel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonFormat(pattern = "dd/MM/yyyy hh:mm")
    @Builder.Default
    private LocalDateTime dataRetirada = LocalDateTime.now();
    @JsonFormat(pattern = "dd/MM/yyyy hh:mm")
    @Builder.Default
    private LocalDateTime dataDevolucao = LocalDateTime.now().plusDays(2);
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Livro> livros;
    @OneToOne(cascade = CascadeType.PERSIST)
    private Locatario locatario;
}
