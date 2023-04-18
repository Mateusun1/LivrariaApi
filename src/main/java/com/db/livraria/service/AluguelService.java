package com.db.livraria.service;

import com.db.livraria.dto.request.CadastroAluguel;
import com.db.livraria.dto.response.AluguelDetails;
import com.db.livraria.model.Aluguel;

public interface AluguelService {
    Aluguel salvar(CadastroAluguel cadastroAluguel);

    AluguelDetails buscarPorId(Long id);

    AluguelDetails buscarPorNomeDoLocador(String nome);

    void deletarAluguel(Long id);
}
