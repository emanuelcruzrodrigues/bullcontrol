package br.com.bullcontrol.api.modulo.estoque.domain.response;

import br.com.bullcontrol.api.modulo.cadastro.domain.LocalizacaoEstoqueDto;
import br.com.bullcontrol.api.modulo.cadastro.domain.LoteDto;
import br.com.bullcontrol.api.modulo.cadastro.domain.OperacaoEstoqueDto;
import br.com.bullcontrol.api.modulo.cadastro.domain.ProdutoDto;
import com.bullcontrol.estoque.domain.TransformacaoMaterialOrigem;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class TransformacaoMateriaisOrigemResponseDto {
    private LoteDto lote;
    private ProdutoDto produto;
    private OperacaoEstoqueDto operacaoEstoqueSaida;
    private LocalizacaoEstoqueDto localizacaoEstoqueSaida;
    private Double quantidadeA;
    private Double quantidadeB;

    public static TransformacaoMateriaisOrigemResponseDto from(TransformacaoMaterialOrigem transformacaoMaterialOrigem) {
        return builder()
                .lote(LoteDto.from(transformacaoMaterialOrigem.getLote()))
                .produto(ProdutoDto.from(transformacaoMaterialOrigem.getProduto()))
                .operacaoEstoqueSaida(OperacaoEstoqueDto.from(transformacaoMaterialOrigem.getOperacaoEstoqueSaida()))
                .localizacaoEstoqueSaida(LocalizacaoEstoqueDto.from(transformacaoMaterialOrigem.getLocalizacaoEstoqueSaida()))
                .build();
    }
}
