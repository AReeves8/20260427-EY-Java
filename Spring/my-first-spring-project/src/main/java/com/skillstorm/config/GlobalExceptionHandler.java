package com.skillstorm.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

// this class will be used to create various handler methods for different types of exception
// we can handle all exception types here, just some, none, whatever
// the annotation we'll use allows the catching of any exceptions thrown in our controller/service/repo tree

// this includes both ControllerAdvice and ResponseBody
// ControllerAdvice basically says to consult this class when processing requests coming in through controllers
// has nothing to do with exceptions by default
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ArithmeticException.class)
    public ResponseEntity<Map<String, Object>> handleMath(ArithmeticException e) {
        
        HashMap<String, Object> response = new HashMap<String, Object>();

        response.put("status", 400);
        response.put("message", e.getMessage());

        return ResponseEntity.badRequest().body(response);
    }

    // one common thing you'll see is global status exceptions being handled centrally
    // i.e., any 400/500-level response entity, rather than being built in each service method
    // will just throw a ResponseStatusException and get handled centrally
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleErrorResponse(ResponseStatusException e) {
        
        HashMap<String, Object> errorObject = new HashMap<String, Object>();

        errorObject.put("status", e.getStatusCode().value());
        errorObject.put("reason", e.getReason());

        return ResponseEntity.status(e.getStatusCode()).body(errorObject);
    }

    // here we'll handle all validation exceptions thrown by @Valid annotations
    // these come with a lot of useful data, but it can be displayed in a cleaner way for the user
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationError(MethodArgumentNotValidException e) {

        Map<String, String> errors = new HashMap<>();

        // going through the errors in the exception and breaking them out for the user
        // would have to get a little fancier to show all of them for each field
        // here, we just get one at a time
        e.getBindingResult().getFieldErrors().forEach(
            error -> {
                errors.put(error.getField(), error.getDefaultMessage());
            }
        );
    
        Map<String, Object> response = new HashMap<>();
        response.put("status", 400);
        response.put("reason", "Validation Errors");
        response.put("errors", errors);

        return ResponseEntity.status(400).body(response);

    }

    // general handling for all other exceptions, returning an empty 404
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Void> handleGeneralException(Exception e) {
        return ResponseEntity.status(404).body(null);
    }

}
