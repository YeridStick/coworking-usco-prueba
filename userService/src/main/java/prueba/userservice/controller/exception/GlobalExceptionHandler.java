package prueba.userservice.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import prueba.userservice.exception.ExcepcionPersonalizada;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ExcepcionPersonalizada.class)
    public ResponseEntity<ErrorResponse> handleExcepcionPersonalizada(ExcepcionPersonalizada ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorMessage());
        return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse("Se produjo un error interno en el servidor");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

