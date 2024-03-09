package br.com.bullcontrol.api.modulo.estoque.domain;

import br.com.bullcontrol.api.modulo.cadastro.domain.LoteDto;
import lombok.Data;

@Data
public class TransformacaoMateriaisOrigemDto {
    private String cdOperacaoEstoqueSaida;
    private String cdLocalizacaoEstoqueSaida;
    private LoteDto lote;
    private String cdProduto;
    private Double quantidadeA;
    private Double quantidadeB;
}
