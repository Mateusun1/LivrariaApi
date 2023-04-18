package com.db.livraria.service;

import com.db.livraria.dto.request.CadastroLivro;
import com.db.livraria.dto.response.LivroDetails;
import com.db.livraria.model.Livro;

import java.util.List;

public interface LivroService {
    Livro salvar(CadastroLivro cadastroLivro);
    List<LivroDetails> buscarLivrosDisponiveisParaAlugar();
    List<LivroDetails> buscarLivrosAlugados();
    Livro buscarLivroPorId(Long id);
    List<LivroDetails> buscarLivroPorAutor(String nome);
    void deletarLivroPorId(Long id);
}
