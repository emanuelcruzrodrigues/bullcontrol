package br.com.bullcontrol.api.modulo.cadastro.domain;

import com.bullcontrol.components.lookup.LookupFilter;
import com.bullcontrol.components.lookup.LookupProperty;
import com.bullcontrol.enums.TipoOperacaoEstoque;
import com.bullcontrol.estoque.domain.OperacaoEstoque;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OperacaoEstoqueDto {

    private String codigo;
    private TipoOperacaoEstoque tipo;
    private String nome;

    public static OperacaoEstoqueDto from(OperacaoEstoque operacaoEstoque) {
        if (operacaoEstoque == null) return null;
        return builder()
                .codigo(operacaoEstoque.getCdOperacaoEstoque())
                .tipo(operacaoEstoque.getDmTipoOperacaoEstoque())
                .nome(operacaoEstoque.getNmOperacaoEstoque())
                .build();
    }
}
