package br.com.bullcontrol.api.modulo.estoque.domain.request;

import br.com.bullcontrol.api.modulo.cadastro.domain.LocalizacaoEstoqueDto;
import br.com.bullcontrol.api.modulo.cadastro.domain.LoteDto;
import br.com.bullcontrol.api.modulo.cadastro.domain.OperacaoEstoqueDto;
import com.bullcontrol.estoque.domain.TransformacaoMaterialOrigem;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class TransformacaoMateriaisOrigemRequestDto {
    private String cdOperacaoEstoqueSaida;
    private String cdLocalizacaoEstoqueSaida;
    private LoteDto lote;
    private String cdProduto;
    private Double quantidadeA;
    private Double quantidadeB;
}
