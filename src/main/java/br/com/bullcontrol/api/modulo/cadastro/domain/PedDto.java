package br.com.bullcontrol.api.modulo.cadastro.domain;

import com.bullcontrol.ped.domain.PED;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PedDto {

    private String numero;
    private String titulo;
    public static PedDto from(PED ped) {
        if (ped == null) return null;
        return builder()
                .numero(ped.getCdPED())
                .titulo(ped.getTitulo())
                .build();
    }
}
