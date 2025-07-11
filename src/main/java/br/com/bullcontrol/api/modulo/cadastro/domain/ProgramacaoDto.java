package br.com.bullcontrol.api.modulo.cadastro.domain;

import br.com.bullcontrol.api.utils.DateUtils;
import com.bullcontrol.enums.AtivoInativo;
import com.bullcontrol.geral.domain.ClienteFornecedor;
import com.bullcontrol.geral.domain.Empresa;
import com.bullcontrol.pcp.domain.Programacao;
import com.bullcontrol.ped.domain.PED;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.joda.time.LocalDate;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProgramacaoDto {

    private Integer id;
    private String nome;
    private String descricao;
    private String identificador;
    private String dtAbertura;
    private AtivoInativo situacao;

    public static ProgramacaoDto from(Programacao programacao) {
        if (programacao == null) return null;
        return builder()
                .id(programacao.getIdProgramacao())
                .dtAbertura(DateUtils.toString(programacao.getDtAbertura()))
                .nome(programacao.getCdProgramacao())
                .descricao(programacao.getTxDescricao())
                .identificador(programacao.getTxIdentificador())
                .situacao(programacao.getDmSituacao())
                .build();
    }
}
