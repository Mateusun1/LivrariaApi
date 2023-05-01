package com.db.livraria.unit.controller;

import com.db.livraria.dto.request.CadastroAutor;
import com.db.livraria.exception.CpfDuplicadoException;
import com.db.livraria.exception.LivroAtreladoException;
import com.db.livraria.exception.NotFoundException;
import com.db.livraria.model.Autor;
import com.db.livraria.service.impl.AutorServiceImpl;
import com.db.livraria.stubs.AutorStub;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AutorControllerTest {
    private static final String URL = "/v1/autor";
    @Autowired
    private MockMvc mvc;
    @Autowired
    private final ObjectMapper objectMapper = new ObjectMapper();
    @MockBean
    private AutorServiceImpl service;


    Autor autor = AutorStub.criarStubAutor();
    CadastroAutor autorCadastrado = AutorStub.criarStubAutorCadastro();

    @Test
    @DisplayName("Deve cadastrar autor com sucesso e retornar 202 CREATED")
    void deveCadastrarAutor() throws Exception {
        when(service.salvar(any(CadastroAutor.class))).thenReturn(autor);
        String autorCadastradoAsJson = objectMapper.writeValueAsString(autorCadastrado);

        mvc.perform(post(URL).content(autorCadastradoAsJson)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.nome").value("Monteiro Lobato"));
    }
    @Test
    @DisplayName("Deve retornar BAD_REQUEST ao cadastrar anoNascimento e gênero nulo")
    void deveRetornarBadRequestAoCadastrarAutor() throws Exception {
        when(service.salvar(any(CadastroAutor.class))).thenReturn(null);

        CadastroAutor cadastroAutorNulo = AutorStub.criarStubAutorCadastroNulo();
        String autorCadastradoAsJson = objectMapper.writeValueAsString(cadastroAutorNulo);

        mvc.perform(post(URL).content(autorCadastradoAsJson)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.id").doesNotExist());
    }
    @Test
    @DisplayName("Deve retornar CONFICT ao cadastrar CPF já existente")
    void deveRetornarConflictAoCadastrarAutorComCpfRepetido() throws Exception {
        when(service.salvar(any(CadastroAutor.class))).thenThrow(CpfDuplicadoException.class);
        String autorCadastradoAsJson = objectMapper.writeValueAsString(autorCadastrado);

        mvc.perform(post(URL).content(autorCadastradoAsJson)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                ).andExpect(status().isConflict())
                .andExpect(jsonPath("$.id").doesNotExist());
    }
    @Test
    @DisplayName("Deve retornar sucesso ao buscar um nome do autor pelo parâmetro")
    void deveRetornarSucessoAoBuscarAutor() throws Exception {
        when(service.buscarAutorPeloNome("Monteiro Lobato")).thenReturn(autor);

        mvc.perform(get(URL + "?nome=Monteiro Lobato")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.nome").value("Monteiro Lobato"));
    }
    @Test
    @DisplayName("Deve retornar NOT_FOUND ao buscar um nome do autor pelo parâmetro")
    void deveRetornarNotFoundAoBuscarAutor() throws Exception {
        when(service.buscarAutorPeloNome(anyString())).thenThrow(NotFoundException.class);

        mvc.perform(get(URL + "?nome=Monteiro Lobato")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                ).andExpect(status().isNotFound());
    }
    @Test
    @DisplayName("Deve retornar sucesso ao deletar um Autor")
    void deveRetornarSucessoAoDeletarAutor() throws Exception {
        mvc.perform(delete(URL + "/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(status().isOk());
    }
    @Test
    @DisplayName("Deve retornar NOT_FOUND ao deletar um Autor")
    void deveRetornarNotFoundAoDeletarAutor() throws Exception {

        doThrow(NotFoundException.class).when(service).deletarAutorPorId(1L);

        mvc.perform(delete(URL + "/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(status().isNotFound());
    }
    @Test
    @DisplayName("Deve retornar CONFICT ao deletar um Autor com livro atrelado ao nome dele")
    void deveRetornarConflictAoDeletarAutor() throws Exception {
        doThrow(LivroAtreladoException.class).when(service).deletarAutorPorId(1L);

        mvc.perform(delete(URL + "/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(status().isConflict());
    }

}
