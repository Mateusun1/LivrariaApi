package com.db.livraria.service.impl;

import com.db.livraria.dto.request.CadastroLivro;
import com.db.livraria.dto.response.LivroDetails;
import com.db.livraria.exception.LivroAlugadoException;
import com.db.livraria.exception.NotFoundException;
import com.db.livraria.mapper.LivroMapper;
import com.db.livraria.model.Autor;
import com.db.livraria.model.Livro;
import com.db.livraria.repository.AutorRepository;
import com.db.livraria.repository.LivroRepository;
import com.db.livraria.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.db.livraria.mapper.LivroMapper.*;

@Service
public class LivroServiceImpl implements LivroService {
    private static final String MESSAGE_NOT_FOUND = "Autor não encontrado";
    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    @Autowired
    public LivroServiceImpl(LivroRepository livroRepository, AutorRepository autorRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    @Override
    public Livro salvar(CadastroLivro cadastroLivro) {
        List<Autor> entidadesId = autorRepository.findAllById(cadastroLivro.getAutoresId());

        if (entidadesId.isEmpty()){
            throw new NotFoundException(MESSAGE_NOT_FOUND);
        }

        Livro livroEntity = toLivro(cadastroLivro, entidadesId);
        livroRepository.save(livroEntity);

        return livroEntity;
    }

    @Override
    public List<LivroDetails> buscarLivrosDisponiveisParaAlugar() {
        return livroRepository.findByAlugadoFalse().stream().map(LivroMapper::toLivroDetails).toList();
    }

    @Override
    public List<LivroDetails> buscarLivrosAlugados() {
        return livroRepository.findByAlugadoTrue().stream().map(LivroMapper::toLivroDetails).toList();
    }

    @Override
    public Livro buscarLivroPorId(Long id) {
        return livroRepository.findById(id).orElseThrow(() -> new NotFoundException("Livro não encontrado"));
    }

    @Override
    public List<LivroDetails> buscarLivroPorAutor(String nome) {
        return livroRepository.findByAutoresNome(nome).stream().map(LivroMapper::toLivroDetails).toList();
    }

    @Override
    public void deletarLivroPorId(Long id) {
        Livro livroEntity = livroRepository.findById(id).orElseThrow(() -> new NotFoundException(MESSAGE_NOT_FOUND));

        if (livroEntity.isAlugado()){
            throw new LivroAlugadoException("Livro alugado não pode ser deletado");
        }

        livroRepository.deleteById(id);
    }
}
