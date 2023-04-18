package com.db.livraria.controller;

import com.db.livraria.dto.request.CadastroAutor;
import com.db.livraria.model.Autor;
import com.db.livraria.model.Livro;
import com.db.livraria.repository.AutorRepository;
import com.db.livraria.repository.LivroRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
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
class AutorControllerTest {
    private static final String URL = "/v1/autor";
    @LocalServerPort
    private int port;
    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private LivroRepository livroRepository;
    @Autowired
    private final ObjectMapper objectMapper = new ObjectMapper();
    private Autor autor;

    @BeforeEach
    void init(){
            RestAssured.port = port;
            autor = Autor.builder()
                    .id(1L)
                    .nome("Joao")
                    .genero("Masculino")
                    .anoNascimento(Year.of(2010))
                    .cpf("53494105049")
                    .build();
             autorRepository.save(autor);

    }

    @Test
    void deveCadastrarAutor() throws Exception {
        String cadastroAutorAsJson = objectMapper.writeValueAsString(
                CadastroAutor.builder()
                .nome("Joao")
                .genero("Masculino")
                .anoNascimento(Year.of(2010))
                .cpf("03234536007")
                .build());

        given()
                .contentType(ContentType.JSON)
                .body(cadastroAutorAsJson)
                .when()
                .post(URL)
                .then()
                .statusCode(201);
    }
    @Test
    void deveRetornarAutorAoBuscarPeloNomeNoParametro(){

        given()
                .contentType(ContentType.JSON)
                .when()
                .get(URL + "?nome=Joao")
                .then()
                .statusCode(200);
    }
    @Test
    void deveDeletarUmAutor(){
        given()
                .contentType(ContentType.JSON)
                .when()
                .delete(URL + "/1")
                .then()
                .statusCode(200);
    }
    @Test
    void naoDeveDeletarUmAutor(){
        livroRepository.save(Livro.builder()
                .id(1L)
                .nome("O Minotauro")
                .isbn("9788525044297")
                .dataPublicacao(LocalDate.of(2020, 11, 11))
                .autores(List.of(autor))
                .build());

        given()
                .contentType(ContentType.JSON)
                .when()
                .delete(URL + "/1")
                .then()
                .statusCode(409);
    }



}
