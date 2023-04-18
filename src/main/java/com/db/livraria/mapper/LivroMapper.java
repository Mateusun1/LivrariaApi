package com.db.livraria.mapper;

import com.db.livraria.dto.request.CadastroLivro;
import com.db.livraria.dto.response.ListagemObrasAutor;
import com.db.livraria.dto.response.LivroDetails;
import com.db.livraria.model.Autor;
import com.db.livraria.model.Livro;

import java.util.List;

public interface LivroMapper {
    static Livro toLivro(CadastroLivro cadastroLivro, List<Autor> entidadesId) {
        return Livro.builder()
                .nome(cadastroLivro.getNome())
                .isbn(cadastroLivro.getIsbn())
                .autores(entidadesId)
                .dataPublicacao(cadastroLivro.getDataPublicacao())
                .build();
    }
    static ListagemObrasAutor toLivro(Livro livro){
        return ListagemObrasAutor.builder()
                .nome(livro.getNome())
                .isbn(livro.getIsbn())
                .dataPublicacao(livro.getDataPublicacao())
                .build();
    }
    static LivroDetails toLivroDetails(Livro livro){
        return LivroDetails
                .builder()
                .nome(livro.getNome())
                .alugado(livro.isAlugado())
                .autores(livro.getAutores().stream().map(AutorMapper::toAutor).toList())
                .build();
    }
}
