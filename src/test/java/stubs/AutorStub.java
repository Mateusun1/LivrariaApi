package stubs;

import com.db.livraria.dto.request.CadastroAutor;
import com.db.livraria.dto.response.AutorDetails;
import com.db.livraria.dto.response.ListagemObrasAutor;
import com.db.livraria.model.Autor;

import java.time.Year;

public class AutorStub {

    public static Autor criarStubAutor(){
        return Autor.builder()
                .id(1L)
                .nome("Monteiro Lobato")
                .genero("Masculino")
                .anoNascimento(Year.of(1882))
                .cpf("57543425947")
                .build();
    }
    public static CadastroAutor criarStubAutorCadastro(){
        return CadastroAutor.builder()
                .nome("Monteiro Lobato")
                .genero("Masculino")
                .anoNascimento(Year.of(1882))
                .cpf("57543425947")
                .build();
    }
    public static AutorDetails criarStubAutorDetails(){
        return AutorDetails.builder()
                .nome("Monteiro Lobato")
                .genero("Masculino")
                .anoNascimento(Year.of(1882))
                .build();
    }
}
