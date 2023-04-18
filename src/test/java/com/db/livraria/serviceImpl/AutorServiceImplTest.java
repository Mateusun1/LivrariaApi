package com.db.livraria.serviceImpl;

import com.db.livraria.dto.request.CadastroAutor;
import com.db.livraria.exception.LivroAtreladoException;
import com.db.livraria.exception.NotFoundException;
import com.db.livraria.model.Autor;
import com.db.livraria.model.Livro;
import com.db.livraria.repository.AutorRepository;
import com.db.livraria.repository.LivroRepository;
import com.db.livraria.service.impl.AutorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AutorServiceImplTest {
    @InjectMocks
    private AutorServiceImpl autorService;
    @Mock
    private AutorRepository autorRepository;
    @Mock
    private LivroRepository livroRepository;
    private Autor autor;
    private Livro livro;

    @BeforeEach
    void init(){
        iniciarEntidades();
    }


    @Test
    void deveSalvarAutor() {
        when(autorRepository.save(any(Autor.class))).thenReturn(autor);


        Autor autorSalvo = autorService.salvar(CadastroAutor.builder()
                .nome("Monteiro Lobato")
                .genero("Masculino")
                .anoNascimento(Year.of(1882))
                .cpf("35337277006")
                .build());

        assertNotNull(autorSalvo);
    }

    @Test
    void deveBuscarAutorPeloNome() {
        when(autorRepository.findByNome("Monteiro Lobato")).thenReturn(Optional.of(autor));

        Autor autorEncontrado = autorService.buscarAutorPeloNome("Monteiro Lobato");

        verify(autorRepository,times(1)).findByNome("Monteiro Lobato");

        assertAll(
                () -> assertNotNull(autorEncontrado),
                () -> assertEquals(autorEncontrado.getNome(), autor.getNome())
        );
    }
    @Test
    void deveRetornarNotFoundExceptionQuandoBuscarAutorQueNaoExiste() {
        when(autorRepository.findByNome("Monteiro Lobato")).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> autorService.buscarAutorPeloNome("Monteiro Lobato"), "Autor não encontrado");
    }
    @Test
    void deletarAutorPorId() {
        when(autorRepository.findById(any())).thenReturn(Optional.of(autor));
        autorService.deletarAutorPorId(1L);
        verify(autorRepository,times(1)).findById(1L);
    }
    @Test
    void naoPodeDeletarAutorLivroAtrelado() {
        livro.getAutores().add(autor);
        when(autorRepository.findById(any())).thenReturn(Optional.of(autor));
        when(livroRepository.findByAutoresNome(anyString()).isPresent()).thenThrow(LivroAtreladoException.class);
        assertThrows(LivroAtreladoException.class, () -> autorService.deletarAutorPorId(1L),"O autor tem livros atrelados a ele, remova os livros antes de remover autor");
    }

    void iniciarEntidades(){
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
                .dataPublicacao(LocalDate.of(2020,11,22))
                .alugado(false)
                .autores(new ArrayList<>())
                .build();
    }
}