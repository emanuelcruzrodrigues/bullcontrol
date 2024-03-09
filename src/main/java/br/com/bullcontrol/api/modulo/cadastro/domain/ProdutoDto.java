package br.com.bullcontrol.api.modulo.cadastro.domain;

import com.bullcontrol.enums.AtivoInativo;
import com.bullcontrol.enums.SimNao;
import com.bullcontrol.estoque.domain.Produto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProdutoDto {

    private String codigo;
    private String nome;
    private AtivoInativo situacao;
    private SimNao controlaEstoque;
    private SimNao controlaLote;
    private SimNao descricaoLoteObrigatoria;

    public static ProdutoDto from(Produto produto) {
        if (produto == null) return null;

        return builder()
                .codigo(produto.getCdProduto())
                .nome(produto.getNmProduto())
                .situacao(produto.getDmSituacao())
                .controlaEstoque(produto.getDmControlaEstoque())
                .controlaLote(produto.getDmControlaLote())
                .descricaoLoteObrigatoria(produto.getDmDescricaoLoteObrigatoria())
                .build();
    }
}
