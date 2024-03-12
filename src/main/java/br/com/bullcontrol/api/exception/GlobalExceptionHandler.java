package br.com.bullcontrol.api.exception;

import com.bullcontrol.exceptions.BullcontrolException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({BullcontrolApiException.class})
    public ResponseEntity<Object> handle(BullcontrolApiException exception) {
        exception.printStackTrace();

        return ResponseEntity
                .status(exception.getHttpStatusCode())
                .body(BullcontrolApiError.builder()
                        .message(exception.getMessage())
                        .build()
                );
    }
    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Object> handle(RuntimeException exception) {
        exception.printStackTrace();

        if (exception instanceof BullcontrolException) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(BullcontrolApiError.builder()
                            .message(((BullcontrolException)exception).getLocalizedMessage())
                            .build()
                    );
        }

        Throwable error = getOrigin(exception);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(BullcontrolApiError.builder()
                        .message(error.getMessage())
                        .build()
                );
    }

    private Throwable getOrigin(Throwable throwable) {
        if (throwable.getCause() == null || throwable.getCause() == throwable) return throwable;
        return getOrigin(throwable.getCause());
    }
}
