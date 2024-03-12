package br.com.bullcontrol.api.modulo.estoque.domain.request;

import br.com.bullcontrol.api.modulo.estoque.domain.response.TransformacaoMateriaisDestinoResponseDto;
import br.com.bullcontrol.api.modulo.estoque.domain.response.TransformacaoMateriaisOrigemResponseDto;
import lombok.Data;

import java.util.List;

@Data
public class TransformacaoMateriaisRequestDto {
    private Integer idEmpresa;
    private String nrPed;
    private Integer idProgramacao;
    private List<TransformacaoMateriaisOrigemRequestDto> origens;
    private List<TransformacaoMateriaisDestinoRequestDto> destinos;

}
