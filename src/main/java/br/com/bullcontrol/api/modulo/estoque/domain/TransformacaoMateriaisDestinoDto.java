package br.com.bullcontrol.api.modulo.estoque.domain;

import br.com.bullcontrol.api.modulo.cadastro.domain.LoteDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class TransformacaoMateriaisDestinoDto {
    private String cdOperacaoEstoqueEntrada;
    private String cdLocalizacaoEstoqueEntrada;
    private LoteDto lote;
    private String cdProduto;
    private Double quantidadeA;
    private Double quantidadeB;
}
