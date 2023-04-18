package com.db.livraria.controller;

import com.db.livraria.dto.request.CadastroLivro;
import com.db.livraria.model.Autor;
import com.db.livraria.model.Livro;
import com.db.livraria.repository.AutorRepository;
import com.db.livraria.repository.LivroRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
 class LivroControllerTest {
    private static final String URL = "/v1/livro";
    @LocalServerPort
    private int port;
    @Autowired
    private LivroRepository livroRepository;
    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private final ObjectMapper mapper = new ObjectMapper();
    private Autor autor;
    private Livro livro;

    @BeforeEach
    void init() {
        RestAssured.port = port;

        autor = Autor.builder()
                .id(1L)
                .nome("Joao")
                .genero("Masculino")
                .anoNascimento(Year.of(2010))
                .cpf("53494105049")
                .build();
        autorRepository.save(autor);

        livro = Livro.builder()
                .id(1L)
                .nome("O Minotauro")
                .isbn("9788525044297")
                .dataPublicacao(LocalDate.of(2020, 11, 11))
                .autores(List.of(autor))
                .build();
    }

    @Test
    void deveSalvarLivro() throws Exception {
        String livroAsJson = mapper.writeValueAsString(CadastroLivro.builder()
                .nome("O Minotauro")
                .isbn("9788525044297")
                .dataPublicacao(LocalDate.of(2020, 11, 11))
                .autoresId(List.of(1L))
                .build());

        given()
                .contentType(ContentType.JSON)
                .body(livroAsJson)
                .when()
                .post(URL)
                .then()
                .statusCode(201);
    }
    @Test
    void naoDeveSalvarLivro() throws Exception {
        String livroAsJson = mapper.writeValueAsString(CadastroLivro.builder()
                .nome("O Minotauro")
                .isbn("9788525044297")
                .dataPublicacao(LocalDate.of(2020, 11, 11))
                .autoresId(List.of(123L))
                .build());

        given()
                .contentType(ContentType.JSON)
                .body(livroAsJson)
                .when()
                .post(URL)
                .then()
                .statusCode(404);
    }

    @Test
    void deveRetornarLivrosParaAlugar() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get(URL)
                .then()
                .statusCode(200);
    }

    @Test
    void deveRetornarLivrosPorId() {
        livroRepository.save(livro);
        given()
                .contentType(ContentType.JSON)
                .when()
                .get(URL + "/1")
                .then()
                .statusCode(200);
    }
    @Test
    void deveRetornarNotFoundAoBuscarLivros() {
        livroRepository.save(livro);
        given()
                .contentType(ContentType.JSON)
                .when()
                .get(URL + "/3")
                .then()
                .statusCode(404);
    }

    @Test
    void deveRetornarLivrosAlugados() {
        livroRepository.save(Livro.builder()
                .id(2L)
                .nome("O Minotauro")
                .isbn("9788525044297")
                .dataPublicacao(LocalDate.of(2020, 11, 11))
                .alugado(true)
                .autores(List.of(autor))
                .build());

        given()
                .contentType(ContentType.JSON)
                .when()
                .get(URL + "/alugado")
                .then()
                .statusCode(200);
    }

    @Test
    void deveDeletarLivros() {
        livroRepository.save(livro);
        given()
                .contentType(ContentType.JSON)
                .when()
                .delete(URL + "/1")
                .then()
                .statusCode(200);
    }
}
