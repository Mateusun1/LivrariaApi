package com.db.livraria.mapper;

import com.db.livraria.dto.request.CadastroAutor;
import com.db.livraria.dto.response.AutorDetails;
import com.db.livraria.model.Autor;

public interface AutorMapper {

    static Autor toAutor(CadastroAutor autorCadastrado){
        return Autor.builder()
                .nome(autorCadastrado.getNome())
                .genero(autorCadastrado.getGenero())
                .anoNascimento(autorCadastrado.getAnoNascimento())
                .cpf(autorCadastrado.getCpf())
                .build();
    }
    static AutorDetails toAutor(Autor autor){
        return AutorDetails.builder()
                .nome(autor.getNome())
                .genero(autor.getGenero())
                .anoNascimento(autor.getAnoNascimento())
                .build();
    }
}
