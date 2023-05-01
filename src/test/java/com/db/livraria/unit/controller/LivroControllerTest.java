package com.db.livraria.unit.controller;

import com.db.livraria.dto.request.CadastroLivro;
import com.db.livraria.dto.response.LivroDetails;
import com.db.livraria.exception.LivroAlugadoException;
import com.db.livraria.exception.NotFoundException;
import com.db.livraria.model.Livro;
import com.db.livraria.service.impl.LivroServiceImpl;
import com.db.livraria.stubs.LivroStub;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class LivroControllerTest {
    private static final String URL = "/v1/livro";
    @Autowired
    private MockMvc mvc;
    @Autowired
    private final ObjectMapper objectMapper = new ObjectMapper();
    @MockBean
    private LivroServiceImpl service;
    Livro livro = LivroStub.criarStubLivro();
    CadastroLivro cadastroLivro = LivroStub.criarStubLivroCadastro();

    @Test
    @DisplayName("Deve cadastrar um Livro com sucesso e retornar 202")
    void DeveCadastrarUmLivro() throws Exception {
        when(service.salvar(any(CadastroLivro.class))).thenReturn(livro);
        String livroCadastradoAsJson = objectMapper.writeValueAsString(cadastroLivro);

        mvc.perform(post(URL)
                .content(livroCadastradoAsJson)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.nome").value("O Minotauro"));
    }
    @Test
    @DisplayName("Não deve cadastrar um Livro com os autores vazios")
    void naoDeveCadastrarUmLivroComAutorVazio() throws Exception {
        when(service.salvar(any(CadastroLivro.class))).thenThrow(NotFoundException.class);
        String livroCadastradoAsJson = objectMapper.writeValueAsString(cadastroLivro);

        mvc.perform(post(URL)
                        .content(livroCadastradoAsJson)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                ).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.id").doesNotExist());
    }
    @Test
    @DisplayName("Não deve cadastrar um Livro com DataPublicacao nulo")
    void naoDeveCadastrarUmLivroComDataPublicacaoNulo() throws Exception {
        when(service.salvar(any(CadastroLivro.class))).thenReturn(null);
        CadastroLivro cadastroLivroNulo = LivroStub.criarStubLivroCadastroComDataPubicacaoNulo();
        String livroCadastradoAsJson = objectMapper.writeValueAsString(cadastroLivroNulo);

        mvc.perform(post(URL)
                        .content(livroCadastradoAsJson)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.id").doesNotExist());
    }
    @Test
    @DisplayName("Não deve cadastrar um Livro com Autores nulos")
    void naoDeveCadastrarUmLivroComAutorNulo() throws Exception {
        when(service.salvar(any(CadastroLivro.class))).thenReturn(null);
        CadastroLivro cadastroLivroNulo = LivroStub.criarStubLivroCadastroComAutoresNulos();
        String livroCadastradoAsJson = objectMapper.writeValueAsString(cadastroLivroNulo);

        mvc.perform(post(URL)
                        .content(livroCadastradoAsJson)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.id").doesNotExist());
    }
    @Test
    @DisplayName("Deve retornar Livros alugados e 200")
    void deveRetornarLivrosAlugados() throws Exception {
        LivroDetails livroDetailsAlugado = LivroStub.criarStubLivroDetailsAlugado();
        when(service.buscarLivrosAlugados()).thenReturn(List.of(livroDetailsAlugado));

        mvc.perform(get(URL + "/alugado")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                ).andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(List.of(livroDetailsAlugado))));
    }
    @Test
    @DisplayName("Deve retornar Livros não alugados e Status Code 200")
    void deveRetornarLivrosNaoAlugados() throws Exception {
        LivroDetails livroDetails = LivroStub.criarStubLivroDetails();
        when(service.buscarLivrosDisponiveisParaAlugar()).thenReturn(List.of(livroDetails));

        mvc.perform(get(URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                ).andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(List.of(livroDetails))));
    }
    @Test
    @DisplayName("Deve retornar o livro buscado pelo ID")
    void deveBuscarLivroPeloId() throws Exception {
        when(service.buscarLivroPorId(anyLong())).thenReturn(livro);

        mvc.perform(get(URL + "/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                ).andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(livro)));
    }
    @Test
    @DisplayName("Deve retornar NOT_FOUND buscando o livro pelo ID")
    void deveRetornarNotFoundAoBuscarPeloIdInvalido() throws Exception {
        when(service.buscarLivroPorId(anyLong())).thenThrow(NotFoundException.class);

        mvc.perform(get(URL + "/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                ).andExpect(status().isNotFound());
    }
    @Test
    @DisplayName("Deve deletar um livro pelo Id")
    void deveDeletarUmLivroPeloId() throws Exception {
        mvc.perform(delete(URL + "/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(status().isOk());
    }
    @Test
    @DisplayName("Deve retornar NOT_FOUND ao passar um ID invalido")
    void deveRetornarNotFoundAoPassarUmIdInvalido() throws Exception {
        doThrow(NotFoundException.class).when(service).deletarLivroPorId(1L);

        mvc.perform(delete(URL + "/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(status().isNotFound());
    }
    @Test
    @DisplayName("Deve retornar CONFLICT ao passar um livro pelo ID")
    void deveRetornarConflictAoPassarUmLivroPeloId() throws Exception {
        doThrow(LivroAlugadoException.class).when(service).deletarLivroPorId(1L);

        mvc.perform(delete(URL + "/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(status().isConflict());
    }
}
