package br.com.bullcontrol.api.modulo.estoque.domain;

import lombok.Data;

import java.util.List;

@Data
public class TransformacaoMateriaisRequestDto {
    private Integer idEmpresa;
    private String nrPed;
    private Integer idProgramacao;
    private List<TransformacaoMateriaisOrigemDto> origens;
    private List<TransformacaoMateriaisDestinoDto> destinos;

}
