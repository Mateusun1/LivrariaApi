package com.db.livraria.stubs;

import com.db.livraria.dto.request.AtualizarLocatario;
import com.db.livraria.dto.request.CadastroLocatario;
import com.db.livraria.dto.response.LocatarioDetails;
import com.db.livraria.model.Locatario;

import java.time.LocalDate;

public class LocatarioStub {

    public static Locatario criarStubLocatario(){
        return Locatario.builder()
                .id(1L)
                .nome("Matheus")
                .genero("Masculino")
                .telefone("54911112222")
                .email("emailteste@email.com")
                .dataNascimento(LocalDate.of(2002,11,15))
                .cpf("49941622809")
                .build();
    }
    public static CadastroLocatario criarStubLocatarioCadastro(){
        return CadastroLocatario.builder()
                .nome("Matheus")
                .genero("Masculino")
                .telefone("54911112222")
                .email("emailteste@email.com")
                .dataNascimento(LocalDate.of(2002,11,15))
                .cpf("49941622809")
                .build();
    }
    public static LocatarioDetails criarStubLocatarioDetails(){
        return LocatarioDetails.builder()
                .nome("Matheus")
                .genero("Masculino")
                .telefone("54911112222")
                .dataNascimento(LocalDate.of(2002,11,15))
                .build();
    }
    public static AtualizarLocatario criarStubLocatarioAtualizado(){
        return AtualizarLocatario.builder()
                .nome("João")
                .genero("Masculino")
                .telefone("54911112222")
                .dataNascimento(LocalDate.of(2002,11,15))
                .build();
    }
}
