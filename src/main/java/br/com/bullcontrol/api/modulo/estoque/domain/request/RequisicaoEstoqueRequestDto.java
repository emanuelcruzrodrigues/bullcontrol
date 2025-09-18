package br.com.bullcontrol.api.modulo.estoque.domain.request;

import br.com.bullcontrol.api.modulo.cadastro.domain.LoteDto;
import lombok.Data;

@Data
public class RequisicaoEstoqueRequestDto {
    private Integer idEmpresa;
    private String cdProduto;
    private LoteDto lote;
    private Integer idProgramacao;
    private String nrPed;
    private String tipo;
    private String cdOperacaoEstoqueSaida;
    private String cdLocalizacaoEstoqueSaida;
    private String cdOperacaoEstoqueEntrada;
    private String cdLocalizacaoEstoqueEntrada;
    private Double quantidadeA;
    private Double quantidadeB;
    private String cdOperacaoEstoqueRegularizacaoSaida;
    private String cdLocalizacaoEstoqueRegularizacaoSaida;
    private String cdOperacaoEstoqueRegularizacaoEntrada;
    private String cdLocalizacaoEstoqueRegularizacaoEntrada;
    private Double quantidadeRegularizacaoA;
    private Double quantidadeRegularizacaoB;
    private String observacoes;

}
