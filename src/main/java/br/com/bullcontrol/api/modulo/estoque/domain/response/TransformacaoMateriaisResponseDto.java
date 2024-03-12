package br.com.bullcontrol.api.modulo.estoque.domain.response;

import br.com.bullcontrol.api.modulo.cadastro.domain.EmpresaDto;
import br.com.bullcontrol.api.modulo.cadastro.domain.PedDto;
import br.com.bullcontrol.api.modulo.cadastro.domain.ProgramacaoDto;
import com.bullcontrol.enums.NormalCancelado;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransformacaoMateriaisResponseDto {
    private Integer id;
    private NormalCancelado situacao;
    private EmpresaDto empresa;
    private ProgramacaoDto programacao;
    private PedDto ped;
    private List<TransformacaoMateriaisOrigemResponseDto> origens;
    private List<TransformacaoMateriaisDestinoResponseDto> destinos;

}
