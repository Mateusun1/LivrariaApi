package com.db.livraria.controller;

import com.db.livraria.dto.request.AtualizarLocatario;
import com.db.livraria.dto.request.CadastroLocatario;
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

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
 class LocatarioControllerTest {
    private static final String URL = "/v1/locatario";
    @LocalServerPort
    private int port;
    @Autowired
    private LocatarioRepository locatarioRepository;
    @Autowired
    private AluguelRepository aluguelRepository;
    @Autowired
    private LivroRepository livroRepository;
    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private final ObjectMapper mapper = new ObjectMapper();
    private Locatario locatario;
    private Autor autor;
    private Livro livro;

    @BeforeEach
    void init(){
        RestAssured.port = port;
        locatario = Locatario.builder()
                .id(1L)
                .nome("Matheus")
                .genero("Masculino")
                .telefone("1122223333")
                .email("teste12@email.com")
                .dataNascimento(LocalDate.of(2022,11,15))
                .cpf("50068883005")
                .build();
        locatarioRepository.save(locatario);
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
    }

    @Test
    void deveSalvarLocatario() throws JsonProcessingException {
        String locatarioAsJson = mapper.writeValueAsString(CadastroLocatario.builder()
                .nome("Joao")
                .genero("Masculino")
                .telefone("1122223333")
                .email("teste2@email.com")
                .dataNascimento(LocalDate.of(2022,11,15))
                .cpf("69157490040").build());

        given()
                .contentType(ContentType.JSON)
                .body(locatarioAsJson)
                .when()
                .post(URL)
                .then()
                .statusCode(201);
    }
    @Test
    void deveRetornarLocatarioPorId() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get(URL + "/1")
                .then()
                .statusCode(200);
    }
    @Test
    void deveRetornarLocatarioPorNome() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get(URL + "?nome=Matheus")
                .then()
                .statusCode(200);
    }
    @Test
    void deveAtualizarLocatario() throws JsonProcessingException {
        String locatarioAtualizadoAsJson = mapper.writeValueAsString(AtualizarLocatario.builder()
                .nome("Matheus")
                .genero("Masculino")
                .telefone("2233334444")
                .email("teste12@email.com")
                .dataNascimento(LocalDate.of(2022,11,15))
                .build());

        given()
                .body(locatarioAtualizadoAsJson)
                .contentType(ContentType.JSON)
                .when()
                .put(URL + "/1")
                .then()
                .statusCode(200);
    }
    @Test
    void deveDeletarLocatario() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .delete(URL + "/1")
                .then()
                .statusCode(200);
    }
    @Test
    void naoDeveDeletarLocatario() {
        aluguelRepository.save(Aluguel.builder()
                .id(1L)
                .livros(List.of(livro))
                .locatario(locatario)
                .build());

        given()
                .contentType(ContentType.JSON)
                .when()
                .delete(URL + "/1")
                .then()
                .statusCode(409);
    }

}
