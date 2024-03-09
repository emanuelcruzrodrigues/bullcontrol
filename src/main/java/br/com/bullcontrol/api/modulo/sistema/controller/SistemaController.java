package br.com.bullcontrol.api.modulo.sistema.controller;

import br.com.bullcontrol.api.exception.BullcontrolApiError;
import br.com.bullcontrol.api.exception.BullcontrolApiException;
import br.com.bullcontrol.api.invoker.BullcontrolService;
import br.com.bullcontrol.api.invoker.BullcontrolServicesInitializer;
import br.com.bullcontrol.api.modulo.sistema.domain.SessaoUsuarioResponseDto;
import br.com.bullcontrol.api.modulo.sistema.transformer.SessaoUsuarioTransformer;
import com.bullcontrol.sistema.domain.SessaoUsuario;
import com.bullcontrol.sistema.service.SessaoUsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/sistema/v1/")
public class SistemaController {

    @BullcontrolService
    private SessaoUsuarioService sessaoUsuarioService;

    @Autowired
    private SessaoUsuarioTransformer sessaoUsuarioTransformer;

    public SistemaController() {
        BullcontrolServicesInitializer.injectResources(this);
    }

    @Operation(summary = "Retorna a sessao do usuario", description = "Retorna a sessao do usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sessao existente",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = SessaoUsuarioResponseDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Nao encontrada a sessao para o usuario",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = BullcontrolApiError.class)) })
    })
    @RequestMapping(value = "sessao-usuario/{nmUsuario}", method = RequestMethod.GET)
    public SessaoUsuarioResponseDto iniciarSessao(@PathVariable @Parameter(name = "nmUsuario", description = "Nome do usuario", example = "sysadmin") String nmUsuario) {
        final SessaoUsuario sessao = sessaoUsuarioService.getSessao(nmUsuario);

        if (sessao == null) throw BullcontrolApiException.builder()
                .httpStatusCode(HttpStatus.NOT_FOUND)
                .message("Sessao nao encontrada")
                .build();

        return sessaoUsuarioTransformer.toSessaoUsuarioResponseDto(sessao);
    }

}
