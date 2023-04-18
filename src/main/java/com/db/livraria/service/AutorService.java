package com.db.livraria.service;

import com.db.livraria.dto.request.CadastroAutor;
import com.db.livraria.model.Autor;

public interface AutorService {
    Autor salvar(CadastroAutor autorCadastrado);
    Autor buscarAutorPeloNome(String nome);
    void deletarAutorPorId(Long id);

}
