package br.com.bullcontrol.api.modulo.cadastro.domain;

import com.bullcontrol.estoque.domain.Lote;
import com.bullcontrol.estoque.domain.LoteFilter;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoteDto {
    private String numero;
    private Double comprimento;
    private Double largura;
    private String observacoes;

    public static LoteDto from(Lote lote) {
        if (lote == null) return null;
        return builder()
                .numero(lote.toString())
                .comprimento(lote.getComprimento())
                .largura(lote.getLargura())
                .observacoes(lote.getObservacoes())
                .build();
    }

    public LoteFilter toLoteFilter() {
        LoteFilter loteFilter = new LoteFilter();
        loteFilter.setLote(getNumero());
        loteFilter.setComprimento(getComprimento());
        loteFilter.setLargura(getLargura());
        loteFilter.setObservacoes(getObservacoes());
        return loteFilter;
    }

    public LoteFilter toLoteFilter(Lote lote) {
        LoteFilter loteFilter = new LoteFilter();
        loteFilter.setLote(getNumero());
        loteFilter.setComprimento(getComprimento() != null ? getComprimento() : lote.getComprimento());
        loteFilter.setLargura(getLargura() != null ? getLargura() : lote.getLargura());
        loteFilter.setObservacoes(StringUtils.isNotBlank(getObservacoes()) ? getObservacoes() : lote.getObservacoes());
        return loteFilter;
    }
}
