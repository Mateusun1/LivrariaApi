package com.db.livraria.mapper;

import com.db.livraria.dto.response.AluguelDetails;
import com.db.livraria.model.Aluguel;
import com.db.livraria.model.Livro;
import com.db.livraria.model.Locatario;

import java.util.List;

public interface AluguelMapper {

    static Aluguel toAluguel(List<Livro> livrosAchados, Locatario locatarioEncontrado){
        return Aluguel.builder()
                .livros(livrosAchados)
                .locatario(locatarioEncontrado)
                .build();
    }


    static AluguelDetails toAluguelDetails(Aluguel aluguel) {
        return AluguelDetails.builder()
                .dataRetirada(aluguel.getDataRetirada())
                .dataDevolucao(aluguel.getDataDevolucao())
                .livros(aluguel.getLivros().stream().map(LivroMapper::toLivroDetails).toList())
                .locatario(LocatarioMapper.toLocatarioDetails(aluguel.getLocatario()))
                .build();
    }
}
