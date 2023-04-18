package com.db.livraria.service.impl;

import com.db.livraria.dto.request.CadastroAluguel;
import com.db.livraria.dto.response.AluguelDetails;
import com.db.livraria.exception.NotFoundException;
import com.db.livraria.model.Aluguel;
import com.db.livraria.model.Livro;
import com.db.livraria.model.Locatario;
import com.db.livraria.repository.AluguelRepository;
import com.db.livraria.repository.LivroRepository;
import com.db.livraria.repository.LocatarioRepository;
import com.db.livraria.service.AluguelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.db.livraria.mapper.AluguelMapper.toAluguel;
import static com.db.livraria.mapper.AluguelMapper.toAluguelDetails;

@Service
public class AluguelServiceImpl implements AluguelService {
    private static final String MESSAGE_NOT_FOUND = "Locatario não encontrado";

    private final AluguelRepository aluguelRepository;
    private final LocatarioRepository locatarioRepository;
    private final LivroRepository livroRepository;
    @Autowired
    public AluguelServiceImpl(AluguelRepository aluguelRepository, LocatarioRepository locatarioRepository, LivroRepository livroRepository) {
        this.aluguelRepository = aluguelRepository;
        this.locatarioRepository = locatarioRepository;
        this.livroRepository = livroRepository;
    }

    @Override
    public Aluguel salvar(CadastroAluguel cadastroAluguel) {
        List<Livro> livrosAchados = livroRepository.findAllById(cadastroAluguel.getIdLivros());

        if (livrosAchados.isEmpty()){
            throw new NotFoundException("Livro não encontrado");
        }

        livrosAchados.forEach(v -> v.setAlugado(true));

        Locatario locatarioEncontrado = locatarioRepository.findById(cadastroAluguel.getIdLocatario())
                .orElseThrow(() -> new NotFoundException(MESSAGE_NOT_FOUND));

        Aluguel aluguel = toAluguel(livrosAchados, locatarioEncontrado);
        aluguelRepository.save(aluguel);

        return aluguel;
    }

    @Override
    public AluguelDetails buscarPorId(Long id) {
        Aluguel aluguel = aluguelRepository.findById(1L).orElseThrow(() -> new NotFoundException("Aluguel não encontrado"));
        return toAluguelDetails(aluguel);
    }

    @Override
    public AluguelDetails buscarPorNomeDoLocador(String nome) {
        Aluguel aluguel = aluguelRepository.findByLocatarioNome(nome)
                .orElseThrow(() -> new NotFoundException(MESSAGE_NOT_FOUND));
        return toAluguelDetails(aluguel);
    }

    @Override
    public void deletarAluguel(Long id) {
        Aluguel aluguel = aluguelRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(MESSAGE_NOT_FOUND));

        aluguel.getLivros().forEach(v -> v.setAlugado(false));

        aluguelRepository.delete(aluguel);
    }
}
