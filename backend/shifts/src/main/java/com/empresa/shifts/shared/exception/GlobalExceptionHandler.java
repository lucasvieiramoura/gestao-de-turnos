package com.empresa.shifts.shared.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;

@RestControllerAdvice 
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleNotFound(ResourceNotFoundException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problem.setTitle("Recurso não encontrado");
        return  problem;
    }

    @ExceptionHandler (BusinessRuleException.class)
    public ProblemDetail handleBusinessRule(BusinessRuleException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage());
        problem.setTitle("Regra de negócio violada");
        return problem;
    }

    @ExceptionHandler (DataIntegrityViolationException.class) 
    public ProblemDetail handleIntegraty (DataIntegrityViolationException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
            HttpStatus.CONFLICT, "A operação viola uma restrição de integridade dos dados");
        problem.setTitle("Conflito de dados");
        return problem;
    }

    @ExceptionHandler (RestClientException.class)
    public ProblemDetail handleExternalService(RestClientException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
            HttpStatus.BAD_GATEWAY, "Falha ao comunicar com o provedor de identidade."
        );
        problem.setTitle("Serviço externo indisponível");
        return problem;
    }
}
