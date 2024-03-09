package br.com.bullcontrol.api.modulo.cadastro.domain;

import com.bullcontrol.geral.domain.Empresa;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmpresaDto {
    private Integer id;
    private String razaoSocial;

    public static EmpresaDto from(Empresa empresa) {
        if (empresa == null) return null;
        return builder()
                .id(empresa.getIdEmpresa())
                .razaoSocial(empresa.getNmRazaoSocial())
                .build();
    }
}
