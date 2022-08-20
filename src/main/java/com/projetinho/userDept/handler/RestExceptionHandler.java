package com.projetinho.userDept.handler;

import com.projetinho.userDept.exception.BadRequestException;
import com.projetinho.userDept.exception.BadRequestExceptionDetails;
import com.projetinho.userDept.exception.ValidationExecptionDetails;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BadRequestExceptionDetails> handlerBadRequestException(BadRequestException badRequestException){
        BadRequestExceptionDetails badRequestExceptionDetails = BadRequestExceptionDetails.builder()
                .title("Bad Request Excepiton, Check the Documentation")
                .status(HttpStatus.BAD_REQUEST.value())
                .details(badRequestException.getMessage())
                .develloperMessage(badRequestException.getClass().getName())
                .timestamp(LocalDateTime.now()).build();

        return new ResponseEntity<>(badRequestExceptionDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationExecptionDetails> handlerMethodArgumentNotValidException(MethodArgumentNotValidException exception){
       List<FieldError> fieldErroList = exception.getBindingResult().getFieldErrors();

       String fields = fieldErroList.stream()
               .map(FieldError::getField)
               .collect(Collectors.joining(","));

        String fieldsMessage = fieldErroList.stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(","));

        ValidationExecptionDetails validationExecptionDetails = ValidationExecptionDetails.builder()
                .fields(fields)
                .fieldsMessage(fieldsMessage)
                .title("Bad Request Exception, Invalid Fields")
                .status(HttpStatus.BAD_REQUEST.value())
                .details("Checked Fields")
                .develloperMessage(exception.getClass().getName())
                .timestamp(LocalDateTime.now()).build();

        return new ResponseEntity<>(validationExecptionDetails, HttpStatus.BAD_REQUEST);

    }
}
