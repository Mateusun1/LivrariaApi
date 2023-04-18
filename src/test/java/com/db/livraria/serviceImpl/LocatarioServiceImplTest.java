package com.db.livraria.serviceImpl;

import com.db.livraria.dto.request.AtualizarLocatario;
import com.db.livraria.dto.request.CadastroLocatario;
import com.db.livraria.exception.AluguelLocatarioException;
import com.db.livraria.exception.NotFoundException;
import com.db.livraria.model.Locatario;
import com.db.livraria.repository.AluguelRepository;
import com.db.livraria.repository.LocatarioRepository;
import com.db.livraria.service.impl.LocatarioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
 class LocatarioServiceImplTest {
    @InjectMocks
    private LocatarioServiceImpl locatarioService;
    @Mock
    private LocatarioRepository locatarioRepository;
    @Mock
    private AluguelRepository aluguelRepository;
    private Locatario locatario;

    @BeforeEach
    void init(){
        locatario = Locatario.builder()
                .id(1L)
                .nome("Matheus")
                .genero("Masculino")
                .telefone("1122223333")
                .email("teste@email.com")
                .dataNascimento(LocalDate.of(2022,11,15))
                .cpf("61077855079")
                .build();
    }
    @Test
    void deveCadastrarLocatario(){
        when(locatarioRepository.save(any(Locatario.class))).thenReturn(locatario);

        locatarioService.salvar(CadastroLocatario.builder().nome("Matheus")
                .genero("Masculino")
                .telefone("1122223333")
                .email("teste@email.com")
                .dataNascimento(LocalDate.of(2022,11,15))
                .cpf("61077855079")
                .build());

    }
    @Test
    void deveRetornarLocatarioBuscadoPorId(){
        when(locatarioRepository.findById(anyLong())).thenReturn(Optional.of(locatario));

        locatarioService.buscarLocatarioPorId(1L);

        verify(locatarioRepository, times(1)).findById(1L);
    }
    @Test
    void deveRetornarExceptionAoBuscarLocatarioNaoExistentePeloId(){
        when(locatarioRepository.findById(anyLong())).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> locatarioService.buscarLocatarioPorId(1L));
    }
    @Test
    void deveRetornarLocatarioAoBuscarPeloNome(){
        when(locatarioRepository.findByNome(anyString())).thenReturn(Optional.of(locatario));

        locatarioService.buscarLocatarioPeloNome("Matheus");

        verify(locatarioRepository, times(1)).findByNome("Matheus");
    }
    @Test
    void deveRetornarExceptionAoBuscarLocatarioNaoExistentePeloNome(){
        when(locatarioRepository.findByNome(anyString())).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> locatarioService.buscarLocatarioPeloNome("Matheus"));
    }
    @Test
    void deveEditarLocatario(){
        when(locatarioRepository.findById(anyLong())).thenReturn(Optional.of(locatario));

        locatarioService.editar(1L, AtualizarLocatario.builder()
                .nome("Matheus")
                .genero("Masculino")
                .telefone("2233334444")
                .email("teste@email.com")
                .dataNascimento(LocalDate.of(2002,11,15))
                .build());

        verify(locatarioRepository, times(1)).findById(1L);
    }
    @Test
    void deveExcluirLocatario(){
        when(locatarioRepository.findById(anyLong())).thenReturn(Optional.of(locatario));
        when(aluguelRepository.findByLocatarioNome(anyString())).thenReturn(Optional.empty());

        locatarioService.deletar(1L);

        verify(locatarioRepository, times(1)).findById(1L);
    }
    @Test
    void naoDeveExcluirLocatario(){
        when(locatarioRepository.findById(anyLong())).thenReturn(Optional.of(locatario));
        when(aluguelRepository.findByLocatarioNome(anyString())).thenThrow(AluguelLocatarioException.class);

        assertThrows(AluguelLocatarioException.class, () -> locatarioService.deletar(1L));
    }
}
