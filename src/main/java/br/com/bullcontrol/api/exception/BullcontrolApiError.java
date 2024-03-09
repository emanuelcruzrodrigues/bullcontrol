package br.com.bullcontrol.api.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BullcontrolApiError {

    @Schema(description = "Mensagem de erro", example = "nmUsuario is marked non-null but is null")
    private String message;
}
