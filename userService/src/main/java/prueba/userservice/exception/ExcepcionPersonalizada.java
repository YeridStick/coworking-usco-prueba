package prueba.userservice.exception;

import org.springframework.http.HttpStatus;

public class ExcepcionPersonalizada extends RuntimeException {
    private final HttpStatus httpStatus;
    private final String errorMessage;

    public ExcepcionPersonalizada(String mensaje, HttpStatus httpStatus) {
        super(mensaje);
        this.httpStatus = httpStatus;
        this.errorMessage = mensaje;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}