package com.db.livraria.service.impl;

import com.db.livraria.dto.request.CadastroAutor;
import com.db.livraria.exception.LivroAtreladoException;
import com.db.livraria.exception.NotFoundException;
import com.db.livraria.model.Autor;
import com.db.livraria.repository.AutorRepository;
import com.db.livraria.repository.LivroRepository;
import com.db.livraria.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.db.livraria.mapper.AutorMapper.toAutor;

@Service
public class AutorServiceImpl implements AutorService {
    private static  final String MESSAGE_NOT_FOUND = "Autor não encontrado";

    private final AutorRepository autorRepository;
    private final LivroRepository livroRepository;
    @Autowired
    public AutorServiceImpl(AutorRepository autorRepository, LivroRepository livroRepository) {
        this.autorRepository = autorRepository;
        this.livroRepository = livroRepository;
    }

    @Override
    public Autor salvar(CadastroAutor autorCadastrado) {
        Autor autorEntity = toAutor(autorCadastrado);
        autorRepository.save(autorEntity);
        return autorEntity;
    }

    @Override
    public Autor buscarAutorPeloNome(String nome) {
        return autorRepository.findByNome(nome)
                .orElseThrow(() -> new NotFoundException(MESSAGE_NOT_FOUND));
    }

    @Override
    public void deletarAutorPorId(Long id) {
        Autor autor = autorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(MESSAGE_NOT_FOUND));

        livroRepository.findByAutoresNome(autor.getNome())
                .ifPresent(v -> {throw new LivroAtreladoException("O autor tem livros atrelados a ele, remova os livros antes de remover autor");});

        autorRepository.deleteById(id);
    }
}
