package br.com.bullcontrol.api.modulo.cadastro.domain;

import com.bullcontrol.estoque.domain.LocalizacaoEstoque;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LocalizacaoEstoqueDto {

    private String codigo;
    private String nome;

    public static LocalizacaoEstoqueDto from(LocalizacaoEstoque localizacaoEstoque) {
        if (localizacaoEstoque == null) return null;

        return builder()
                .codigo(localizacaoEstoque.getCdLocalizacaoEstoque())
                .nome(localizacaoEstoque.getNmLocalizacaoEstoque())
                .build();
    }
}
