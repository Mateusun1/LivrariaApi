package com.db.livraria.controller;

import com.db.livraria.dto.request.CadastroLivro;
import com.db.livraria.dto.response.LivroDetails;
import com.db.livraria.model.Livro;
import com.db.livraria.service.impl.LivroServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/livro")
public class LivroController {
    private final LivroServiceImpl livroService;
    @Autowired
    public LivroController(LivroServiceImpl livroService) {
        this.livroService = livroService;
    }

    @PostMapping
    public ResponseEntity<Livro> salvarLivro(@RequestBody @Valid CadastroLivro cadastroLivro){
        return new ResponseEntity<>(livroService.salvar(cadastroLivro), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<LivroDetails>> buscarLivroDisponiveisParaAlugar(){
        return new ResponseEntity<>(livroService.buscarLivrosDisponiveisParaAlugar(), HttpStatus.OK);
    }
    @GetMapping("/alugado")
    public ResponseEntity<List<LivroDetails>> buscarLivrosAlugados(){
        return new ResponseEntity<>(livroService.buscarLivrosAlugados(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livro> buscarLivroPorId(@PathVariable Long id){
        return new ResponseEntity<>(livroService.buscarLivroPorId(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletarLivroPorId(@PathVariable Long id){
        livroService.deletarLivroPorId(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
