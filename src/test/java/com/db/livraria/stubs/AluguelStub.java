package com.db.livraria.stubs;

import com.db.livraria.dto.request.CadastroAluguel;
import com.db.livraria.dto.response.AluguelDetails;
import com.db.livraria.dto.response.LivroDetails;
import com.db.livraria.dto.response.LocatarioDetails;
import com.db.livraria.model.Aluguel;
import com.db.livraria.model.Livro;
import com.db.livraria.model.Locatario;

import java.time.LocalDateTime;
import java.util.List;

public class AluguelStub {

    public static Aluguel criarStubAluguel(){
        Livro livro = LivroStub.criarStubLivro();
        Locatario locatario = LocatarioStub.criarStubLocatario();
        return Aluguel.builder()
                .id(1L)
                .dataRetirada(LocalDateTime.of(2023,4,28,21,0))
                .dataDevolucao(LocalDateTime.of(2023,4,30,21,0))
                .livros(List.of(livro))
                .locatario(locatario)
                .build();
    }
    public static CadastroAluguel criarStubAluguelCadastro(){
        return CadastroAluguel.builder()
                .idLivros(List.of(1L))
                .idLocatario(1L)
                .build();
    }
    public static AluguelDetails criarStubAluguelDetails(){
        LivroDetails livro = LivroStub.criarStubLivroDetails();
        LocatarioDetails locatario = LocatarioStub.criarStubLocatarioDetails();
        return AluguelDetails.builder()
                .dataRetirada(LocalDateTime.of(2023,4,28,21,0))
                .dataDevolucao(LocalDateTime.of(2023,4,30,21,0))
                .livros(List.of(livro))
                .locatario(locatario)
                .build();
    }
}
