package nandhas.common.exception;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * GlobalExceptionHandler
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    public GlobalExceptionHandler() {
        super();
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleResourceNotFoundException(final Exception ex, WebRequest request,
            HttpServletResponse response) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        CustomError customError = ex.getClass().getAnnotation(CustomError.class);
        if (customError != null) {
            ResponseStatus responseStatus = ex.getClass().getAnnotation(ResponseStatus.class);
            headers.add("errorCode", customError.code());
            return handleExceptionInternal(ex, ex.getMessage(), headers, responseStatus.code(), request);
        }
        else {
            return handleException(ex, request);
        }
    }

}