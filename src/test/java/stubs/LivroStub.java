package stubs;

import com.db.livraria.dto.request.CadastroAutor;
import com.db.livraria.dto.request.CadastroLivro;
import com.db.livraria.dto.response.AutorDetails;
import com.db.livraria.dto.response.ListagemObrasAutor;
import com.db.livraria.dto.response.LivroDetails;
import com.db.livraria.model.Autor;
import com.db.livraria.model.Livro;

import java.time.LocalDate;
import java.util.List;

public class LivroStub {

    public static Livro criarStubLivro(){
        Autor autor = AutorStub.criarStubAutor();
        return Livro.builder()
                .id(1L)
                .nome("O Minotauro")
                .isbn("9788525044297")
                .dataPublicacao(LocalDate.of(2020, 11, 11))
                .alugado(false)
                .autores(List.of(autor))
                .build();
    }

    public static CadastroLivro criarStubLivroCadastro(){
        return CadastroLivro.builder()
                .nome("O Minotauro")
                .isbn("9788525044297")
                .dataPublicacao(LocalDate.of(2020, 11, 11))
                .autoresId(List.of(1L))
                .build();
    }
    public static LivroDetails criarStubLivroDetails(){
        AutorDetails autorDetails = AutorStub.criarStubAutorDetails();
        return LivroDetails.builder()
                .nome("O Minotauro")
                .alugado(false)
                .autores(List.of(autorDetails))
                .build();
    }
    public static ListagemObrasAutor criarStubLivrosAutor(){
        return ListagemObrasAutor.builder()
                .nome("O Minotauro")
                .isbn("9788525044297")
                .dataPublicacao(LocalDate.of(2020, 11, 11))
                .build();
    }
}
