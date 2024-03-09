package br.com.bullcontrol.api.modulo.sistema.domain;

import com.bullcontrol.enums.AtivoInativo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SessaoUsuarioResponseDto {
    @Schema(example = "sysadmin")
    private String idSessao;
    @Schema(description = "Nome do usuario", example = "sysadmin")
    private String nmUsuario;
    @Schema(description = "Identificador da empresa", example = "1")
    private Integer idEmpresa;
    @Schema(description = "Identificador do Locale utilizado pelo usuario", example = "pt_BR")
    private String locale;
    @Schema(example = "10.1.1.100")
    private String ip;
    @Schema(description = "Versao do Java em uso", example = "1.7")
    private String javaVersion;
    @Schema(description = "Sistema operacional", example = "Windows 10")
    private String os;
    @Schema(description = "Data/hora em que a sessao foi criada", example = "2023-03-05 20:00:00")
    private String dtHrCriacao;
    @Schema(description = "Data/hora da ultima interacao do usuario", example = "2023-03-05 20:00:00")
    private String dtHrUltimaIteracao;
    @Schema(description = "Ultima interacao do usuario", example = "sessaoUsuarioService.getSessao")
    private String nmUltimaIteracao;
    @Schema(description = "Situacao da sessao", example = "ATIVO")
    private AtivoInativo dmSituacao;

}
