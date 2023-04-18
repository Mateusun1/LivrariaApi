package com.db.livraria.controller;

import com.db.livraria.dto.request.CadastroAutor;
import com.db.livraria.model.Autor;
import com.db.livraria.service.impl.AutorServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/autor")
public class AutorController {
    private final AutorServiceImpl autorServiceImpl;
    @Autowired
    public AutorController(AutorServiceImpl autorServiceImpl) {
        this.autorServiceImpl = autorServiceImpl;
    }

    @PostMapping
    public ResponseEntity<Autor> salvarAutor(@RequestBody @Valid CadastroAutor autorCadastrado){
        return new ResponseEntity<>(autorServiceImpl.salvar(autorCadastrado), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Autor> buscarAutorPorNome(@RequestParam String nome){
        return new ResponseEntity<>(autorServiceImpl.buscarAutorPeloNome(nome), HttpStatus.OK);
    }
    @DeleteMapping("/{idAutor}")
    public ResponseEntity<HttpStatus> deletarAutor(@PathVariable Long idAutor){
        autorServiceImpl.deletarAutorPorId(idAutor);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
