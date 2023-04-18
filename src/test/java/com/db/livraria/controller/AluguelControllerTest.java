package com.db.livraria.controller;

import com.db.livraria.dto.request.CadastroAluguel;
import com.db.livraria.model.Aluguel;
import com.db.livraria.model.Autor;
import com.db.livraria.model.Livro;
import com.db.livraria.model.Locatario;
import com.db.livraria.repository.AluguelRepository;
import com.db.livraria.repository.AutorRepository;
import com.db.livraria.repository.LivroRepository;
import com.db.livraria.repository.LocatarioRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
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

import static io.restassured.RestAssured.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
class AluguelControllerTest {
    private static final String URL = "/v1/aluguel";
    @LocalServerPort
    private int port;
    @Autowired
    private AluguelRepository aluguelRepository;
    @Autowired
    private LocatarioRepository locatarioRepository;
    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private LivroRepository livroRepository;
    @Autowired
    private final ObjectMapper objectMapper = new ObjectMapper();
    private Autor autor;
    private Livro livro;
    private Locatario locatario;
    private Aluguel aluguel;

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
        livroRepository.save(livro);

        locatario = Locatario.builder()
                .id(1L)
                .nome("Matheus")
                .genero("Masculino")
                .telefone("1122223333")
                .email("teste12@email.com")
                .dataNascimento(LocalDate.of(2022, 11, 15))
                .cpf("50068883005")
                .build();
        locatarioRepository.save(locatario);

        aluguel = Aluguel.builder()
                .id(1L)
                .livros(List.of(livro))
                .locatario(locatario)
                .build();

    }
    @Test
    void deveSalvarUmAluguel() throws JsonProcessingException {
        String aluguelAsJson = objectMapper.writeValueAsString(CadastroAluguel.builder()
                .idLivros(List.of(1L))
                .idLocatario(1L).build());

        given()
                .contentType(ContentType.JSON)
                .body(aluguelAsJson)
                .when()
                .post(URL)
                .then()
                .statusCode(201);
    }
    @Test
    void naoDeveSalvarUmAluguel() throws JsonProcessingException {
        String aluguelAsJson = objectMapper.writeValueAsString(CadastroAluguel.builder()
                .idLivros(List.of(1123L))
                .idLocatario(1L).build());

        given()
                .contentType(ContentType.JSON)
                .body(aluguelAsJson)
                .when()
                .post(URL)
                .then()
                .statusCode(404);
    }
    @Test
    void deveRetornarUmAluguelPorId(){
        aluguelRepository.save(aluguel);
        given()
                .contentType(ContentType.JSON)
                .when()
                .get(URL + "/1")
                .then()
                .statusCode(200);
    }
    @Test
    void deveRetornarUmAluguelPorNomeLocador(){
        aluguelRepository.save(aluguel);
        given()
                .contentType(ContentType.JSON)
                .when()
                .get(URL + "?nome=Matheus")
                .then()
                .statusCode(200);
    }
    @Test
    void deveDeletarAluguel(){
        aluguelRepository.save(aluguel);
        given()
                .contentType(ContentType.JSON)
                .when()
                .delete(URL + "/1")
                .then()
                .statusCode(200);
    }

}
