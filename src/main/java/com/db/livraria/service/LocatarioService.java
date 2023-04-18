package com.db.livraria.service;

import com.db.livraria.dto.request.AtualizarLocatario;
import com.db.livraria.dto.request.CadastroLocatario;
import com.db.livraria.model.Locatario;

public interface LocatarioService {

    Locatario salvar(CadastroLocatario locatarioCadastrado);

    Locatario buscarLocatarioPorId(Long id);

    Locatario buscarLocatarioPeloNome(String nome);

    Locatario editar(Long id,AtualizarLocatario locatario);

    void deletar(Long id);
}
