package com.db.livraria.serviceImpl;

import com.db.livraria.dto.request.CadastroLivro;
import com.db.livraria.exception.LivroAlugadoException;
import com.db.livraria.model.Autor;
import com.db.livraria.model.Livro;
import com.db.livraria.repository.AutorRepository;
import com.db.livraria.repository.LivroRepository;
import com.db.livraria.service.impl.LivroServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
 class LivroServiceImplTest {

    @InjectMocks
    private LivroServiceImpl livroService;
    @Mock
    private LivroRepository livroRepository;
    @Mock
    private AutorRepository autorRepository;
    private Livro livro;
    private Autor autor;

    @BeforeEach
    void init(){
        autor = Autor.builder()
                .id(1L)
                .nome("Monteiro Lobato")
                .genero("Masculino")
                .anoNascimento(Year.of(1882))
                .cpf("35337277006")
                .build();

        livro = Livro.builder()
                .id(1L)
                .nome("O Minotauro")
                .isbn("9788525044297")
                .dataPublicacao(LocalDate.of(2020,11,11))
                .autores(List.of(autor))
                .build();
    }

    @Test
    void deveSalvarLivro(){
        when(autorRepository.findAllById(List.of(1L))).thenReturn(List.of(autor));
        when(livroRepository.save(any(Livro.class))).thenReturn(livro);

        livroService.salvar(CadastroLivro.builder()
                .nome("O Minotauro")
                .isbn("9788525044297")
                .dataPublicacao(LocalDate.of(2020,11,11))
                .autoresId(List.of(1L))
                .build());
    }

    @Test
    void deveRetornarLivrosNaoAlugados(){
        when(livroRepository.findByAlugadoFalse()).thenReturn(List.of(livro));

        livroService.buscarLivrosDisponiveisParaAlugar();

        verify(livroRepository, times(1)).findByAlugadoFalse();
    }
    @Test
    void deveRetornarLivrosAlugados(){
        when(livroRepository.findByAlugadoTrue()).thenReturn(List.of(livro));

        livroService.buscarLivrosAlugados();

        verify(livroRepository, times(1)).findByAlugadoTrue();
    }
    @Test
    void deveRetornarLivrosEscritoPorAutores(){
        when(livroRepository.findByAutoresNome(anyString())).thenReturn(Optional.of(livro));

        livroService.buscarLivroPorAutor("Monteiro Lobato");
        verify(livroRepository, times(1)).findByAutoresNome("Monteiro Lobato");
    }
    @Test
    void deveRetornarLivrosPorId(){
        when(livroRepository.findById(anyLong())).thenReturn(Optional.of(livro));

        livroService.buscarLivroPorId(1L);
    }

    @Test
    void deveDeletarLivro(){
        when(livroRepository.findById(anyLong())).thenReturn(Optional.of(livro));

        livroService.deletarLivroPorId(1L);
    }

    @Test
    void naoDeveDeletarLivroAlugado(){
        when(livroRepository.findById(anyLong())).thenReturn(Optional.of(livro));
        livro.setAlugado(true);

        assertThrows(LivroAlugadoException.class, () -> livroService.deletarLivroPorId(1L));
    }
}
