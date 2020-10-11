package com.example.demo.provider.configuration;

import com.example.demo.provider.exception.NotaConflictDeleteException;
import com.example.demo.provider.exception.NotaNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class NoteExceptionController {

    @ExceptionHandler(value = NotaNotFoundException.class)
    public ResponseEntity<Object> exception(Exception exception) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = NotaConflictDeleteException.class)
    public ResponseEntity<Object> deleteConflict(Exception exception) {
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }


    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> everyException(Exception exception) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
