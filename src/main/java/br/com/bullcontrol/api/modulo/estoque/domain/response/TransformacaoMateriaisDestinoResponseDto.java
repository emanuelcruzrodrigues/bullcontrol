package br.com.bullcontrol.api.modulo.estoque.domain.response;

import br.com.bullcontrol.api.modulo.cadastro.domain.LocalizacaoEstoqueDto;
import br.com.bullcontrol.api.modulo.cadastro.domain.LoteDto;
import br.com.bullcontrol.api.modulo.cadastro.domain.OperacaoEstoqueDto;
import br.com.bullcontrol.api.modulo.cadastro.domain.ProdutoDto;
import com.bullcontrol.estoque.domain.TransformacaoMaterialDestino;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransformacaoMateriaisDestinoResponseDto {
    private LoteDto lote;
    private ProdutoDto produto;
    private OperacaoEstoqueDto operacaoEstoqueEntrada;
    private LocalizacaoEstoqueDto localizacaoEstoqueEntrada;
    private Double quantidadeA;
    private Double quantidadeB;

    public static TransformacaoMateriaisDestinoResponseDto from(TransformacaoMaterialDestino transformacaoMaterialDestino) {
        return builder()
                .lote(LoteDto.from(transformacaoMaterialDestino.getLote()))
                .produto(ProdutoDto.from(transformacaoMaterialDestino.getProduto()))
                .operacaoEstoqueEntrada(OperacaoEstoqueDto.from(transformacaoMaterialDestino.getOperacaoEstoqueEntrada()))
                .localizacaoEstoqueEntrada(LocalizacaoEstoqueDto.from(transformacaoMaterialDestino.getLocalizacaoEstoqueEntrada()))
                .build();
    }
}
