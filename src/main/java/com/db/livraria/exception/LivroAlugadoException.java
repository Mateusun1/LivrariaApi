package com.db.livraria.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class LivroAlugadoException extends RuntimeException{

    public LivroAlugadoException(String msg){
        super(msg);
    }
}
