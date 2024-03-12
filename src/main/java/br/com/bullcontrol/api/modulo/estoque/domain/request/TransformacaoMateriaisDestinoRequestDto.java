package br.com.bullcontrol.api.modulo.estoque.domain.request;

import br.com.bullcontrol.api.modulo.cadastro.domain.LocalizacaoEstoqueDto;
import br.com.bullcontrol.api.modulo.cadastro.domain.LoteDto;
import br.com.bullcontrol.api.modulo.cadastro.domain.OperacaoEstoqueDto;
import com.bullcontrol.estoque.domain.TransformacaoMaterialDestino;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransformacaoMateriaisDestinoRequestDto {
    private String cdOperacaoEstoqueEntrada;
    private String cdLocalizacaoEstoqueEntrada;
    private LoteDto lote;
    private String cdProduto;
    private Double quantidadeA;
    private Double quantidadeB;
}
