package br.com.bullcontrol.api.modulo.estoque.transformer;

import br.com.bullcontrol.api.modulo.cadastro.domain.EmpresaDto;
import br.com.bullcontrol.api.modulo.cadastro.domain.PedDto;
import br.com.bullcontrol.api.modulo.cadastro.domain.ProgramacaoDto;
import br.com.bullcontrol.api.modulo.cadastro.service.DomainQueryService;
import br.com.bullcontrol.api.modulo.estoque.domain.request.RequisicaoEstoqueRequestDto;
import br.com.bullcontrol.api.modulo.estoque.domain.response.RequisicaoEstoqueResponseDto;
import com.bullcontrol.enums.NormalCancelado;
import com.bullcontrol.enums.TipoOperacaoEstoque;
import com.bullcontrol.estoque.domain.Lote;
import com.bullcontrol.estoque.domain.LoteFilter;
import com.bullcontrol.estoque.domain.RequisicaoEstoque;
import lombok.RequiredArgsConstructor;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RequisicaoEstoqueTransformer {

    private final DomainQueryService domainQueryService;
    public RequisicaoEstoque toTransformacaoMaterial(RequisicaoEstoqueRequestDto requestDto) {
        RequisicaoEstoque requisicaoEstoque = new RequisicaoEstoque();
        requisicaoEstoque.setDtRequisicaoEstoque(new LocalDate());
        requisicaoEstoque.setHrRequisicaoEstoque(new LocalTime());
        requisicaoEstoque.setDmSituacao(NormalCancelado.NORMAL);

        requisicaoEstoque.setQuantidadeA(requestDto.getQuantidadeA());
        requisicaoEstoque.setQuantidadeB(requestDto.getQuantidadeB());

        requisicaoEstoque.setQuantidadeRegularizacaoA(Optional.ofNullable(requestDto.getQuantidadeRegularizacaoA()).orElse(0D));
        requisicaoEstoque.setQuantidadeRegularizacaoB(Optional.ofNullable(requestDto.getQuantidadeRegularizacaoB()).orElse(0D));

        requisicaoEstoque.setEmpresa(domainQueryService.getEmpresa(requestDto.getIdEmpresa()));
        requisicaoEstoque.setProduto(domainQueryService.getProduto(requestDto.getCdProduto()));
        requisicaoEstoque.setPed(domainQueryService.getPed(requestDto.getNrPed()));
        requisicaoEstoque.setProgramacao(domainQueryService.getProgramacao(requestDto.getIdProgramacao()));

        Optional.ofNullable(requestDto.getLote()).ifPresent(lote -> {
            Lote loadedLote = domainQueryService.getLoteByNumero(requestDto.getLote().getNumero());
            requisicaoEstoque.setLote(loadedLote);
            LoteFilter loteFilter = requestDto.getLote().toLoteFilter(loadedLote);
            requisicaoEstoque.setLoteFilter(loteFilter);
        });

        requisicaoEstoque.setOperacaoEstoqueEntrada(domainQueryService.getOperacaoEstoque(requestDto.getCdOperacaoEstoqueEntrada()));
        requisicaoEstoque.setOperacaoEstoqueSaida(domainQueryService.getOperacaoEstoque(requestDto.getCdOperacaoEstoqueSaida()));
        requisicaoEstoque.setOperacaoEstoqueEntradaRegularizacao(domainQueryService.getOperacaoEstoque(requestDto.getCdOperacaoEstoqueRegularizacaoEntrada()));
        requisicaoEstoque.setOperacaoEstoqueSaidaRegularizacao(domainQueryService.getOperacaoEstoque(requestDto.getCdOperacaoEstoqueRegularizacaoSaida()));

        requisicaoEstoque.setLocalizacaoEstoqueEntrada(domainQueryService.getLocalizacaoEstoque(requestDto.getCdLocalizacaoEstoqueEntrada()));
        requisicaoEstoque.setLocalizacaoEstoqueSaida(domainQueryService.getLocalizacaoEstoque(requestDto.getCdLocalizacaoEstoqueSaida()));
        requisicaoEstoque.setLocalizacaoEstoqueEntradaRegularizacao(domainQueryService.getLocalizacaoEstoque(requestDto.getCdLocalizacaoEstoqueRegularizacaoEntrada()));
        requisicaoEstoque.setLocalizacaoEstoqueSaidaRegularizacao(domainQueryService.getLocalizacaoEstoque(requestDto.getCdLocalizacaoEstoqueRegularizacaoSaida()));

        Optional.ofNullable(requestDto.getTipo()).ifPresent(tipo ->
            Arrays.stream(TipoOperacaoEstoque.values())
                    .filter(tipoOperacaoEstoque -> tipoOperacaoEstoque.getValue().equals(tipo))
                    .findFirst()
                    .ifPresent(requisicaoEstoque::setDmTipoOperacaoEstoque)
        );

        return requisicaoEstoque;
    }

    public RequisicaoEstoqueResponseDto toRequisicaoEstoqueResponseDto(RequisicaoEstoque requisicaoEstoque) {
        return RequisicaoEstoqueResponseDto.builder()
                .id(requisicaoEstoque.getIdRequisicaoEstoque())
                .situacao(requisicaoEstoque.getDmSituacao())
                .empresa(EmpresaDto.from(requisicaoEstoque.getEmpresa()))
                .ped(PedDto.from(requisicaoEstoque.getPed()))
                .programacao(ProgramacaoDto.from(requisicaoEstoque.getProgramacao()))
                .build();
    }
}
