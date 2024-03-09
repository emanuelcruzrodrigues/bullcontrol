package br.com.bullcontrol.api.exception;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatusCode;

@Data
@Builder
public class BullcontrolApiException extends RuntimeException {
    private HttpStatusCode httpStatusCode;
    private Long code;
    private String message;
}
