package com.lojadejogos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericException(Exception ex) {

        String mensagem = ex.getMessage();
        String mensagemExtraida = extrairMensagemProblem(mensagem);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(mensagemExtraida != null ? mensagemExtraida : "Erro inesperado: " + mensagem);
    }

    private String extrairMensagemProblem(String mensagem) {
        if (mensagem == null) return null;

        Pattern pattern = Pattern.compile("problem:\\s*(.+)");
        Matcher matcher = pattern.matcher(mensagem);
        if (matcher.find()) {
            return matcher.group(1).trim();
        }
        return null;
    }
}
