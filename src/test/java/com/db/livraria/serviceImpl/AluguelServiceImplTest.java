package com.db.livraria.serviceImpl;

import com.db.livraria.dto.request.CadastroAluguel;
import com.db.livraria.mapper.AluguelMapper;
import com.db.livraria.model.Aluguel;
import com.db.livraria.model.Autor;
import com.db.livraria.model.Livro;
import com.db.livraria.model.Locatario;
import com.db.livraria.repository.AluguelRepository;
import com.db.livraria.repository.LivroRepository;
import com.db.livraria.repository.LocatarioRepository;
import com.db.livraria.service.impl.AluguelServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
 class AluguelServiceImplTest {
    @InjectMocks
    private AluguelServiceImpl aluguelService;
    @Mock
    private AluguelRepository aluguelRepository;
    @Mock
    private LocatarioRepository locatarioRepository;
    @Mock
    private LivroRepository livroRepository;
    private Aluguel aluguel;
    private Livro livro;
    private Autor autor;
    private Locatario locatario;

    @BeforeEach
    void init(){
        iniciarVariaveis();
    }
    @Test
    void deveSalvarUmAluguel(){
        when(livroRepository.findAllById(List.of(1L))).thenReturn(List.of(livro));
        when(locatarioRepository.findById(1L)).thenReturn(Optional.of(locatario));

        aluguelService.salvar(CadastroAluguel.builder()
                .idLivros(List.of(1L))
                .idLocatario(1L)
                .build());
    }
    @Test
    void deveBuscarAluguelPorId(){
        when(aluguelRepository.findById(1L)).thenReturn(Optional.of(aluguel));

        aluguelService.buscarPorId(1L);
        verify(aluguelRepository, times(1)).findById(1L);
    }
    @Test
    void deveBuscarAluguelPorNomeDoLocatario(){
        when(aluguelRepository.findByLocatarioNome(anyString())).thenReturn(Optional.of(aluguel));

        aluguelService.buscarPorNomeDoLocador("Matheus");
        verify(aluguelRepository, times(1)).findByLocatarioNome("Matheus");
    }
    @Test
    void deveDeletarAluguel(){
        when(aluguelRepository.findById(1L)).thenReturn(Optional.of(aluguel));

        aluguelService.deletarAluguel(1L);
        verify(aluguelRepository, times(1)).findById(1L);
    }

    void iniciarVariaveis(){
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
                .autores(List.of(autor))
                .build();

        locatario = Locatario.builder()
                .id(1L)
                .nome("Matheus")
                .genero("Masculino")
                .telefone("1122223333")
                .email("teste@email.com")
                .dataNascimento(LocalDate.of(2022,11,15))
                .cpf("61077855079")
                .build();

        aluguel = Aluguel.builder()
                .id(1L)
                .dataRetirada(LocalDateTime.now())
                .dataDevolucao(LocalDateTime.now().withHour(22))
                .livros(List.of(livro))
                .locatario(locatario)
                .build();
    }
}
