package com.db.livraria.controller;

import com.db.livraria.dto.response.AluguelDetails;
import com.db.livraria.dto.request.CadastroAluguel;
import com.db.livraria.model.Aluguel;
import com.db.livraria.service.impl.AluguelServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/aluguel")
public class AluguelController {
    private final AluguelServiceImpl aluguelService;
    @Autowired
    public AluguelController(AluguelServiceImpl aluguelService) {
        this.aluguelService = aluguelService;
    }

    @PostMapping
    public ResponseEntity<Aluguel> salvarAluguel(@RequestBody @Valid CadastroAluguel cadastroAluguel){
        return new ResponseEntity<>(aluguelService.salvar(cadastroAluguel), HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<AluguelDetails> buscarAluguelPorId(@PathVariable Long id){
        return new ResponseEntity<>(aluguelService.buscarPorId(id), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<AluguelDetails> buscarAluguelPorNomeDoLocador(@RequestParam String nome){
        return new ResponseEntity<>(aluguelService.buscarPorNomeDoLocador(nome), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletarAluguel(@PathVariable Long id){
        aluguelService.deletarAluguel(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
