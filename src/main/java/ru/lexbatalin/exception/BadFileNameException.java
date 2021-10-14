package ru.lexbatalin.exception;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Bad filename")
@SuppressWarnings("serial")
@NoArgsConstructor
public class BadFileNameException extends Exception{

    public BadFileNameException(String message) {
        super(message);
    }
}
