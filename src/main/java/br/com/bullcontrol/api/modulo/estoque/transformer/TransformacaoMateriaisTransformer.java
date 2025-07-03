package br.com.bullcontrol.api.modulo.estoque.transformer;

import br.com.bullcontrol.api.modulo.cadastro.domain.EmpresaDto;
import br.com.bullcontrol.api.modulo.cadastro.domain.PedDto;
import br.com.bullcontrol.api.modulo.cadastro.domain.ProgramacaoDto;
import br.com.bullcontrol.api.modulo.cadastro.service.DomainQueryService;
import br.com.bullcontrol.api.modulo.estoque.domain.request.TransformacaoMateriaisDestinoRequestDto;
import br.com.bullcontrol.api.modulo.estoque.domain.request.TransformacaoMateriaisOrigemRequestDto;
import br.com.bullcontrol.api.modulo.estoque.domain.response.TransformacaoMateriaisDestinoResponseDto;
import br.com.bullcontrol.api.modulo.estoque.domain.response.TransformacaoMateriaisOrigemResponseDto;
import br.com.bullcontrol.api.modulo.estoque.domain.request.TransformacaoMateriaisRequestDto;
import br.com.bullcontrol.api.modulo.estoque.domain.response.TransformacaoMateriaisResponseDto;
import com.bullcontrol.enums.NormalCancelado;
import com.bullcontrol.estoque.domain.*;
import lombok.RequiredArgsConstructor;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TransformacaoMateriaisTransformer {

    private final DomainQueryService domainQueryService;

    public TransformacaoMaterial toTransformacaoMaterial(TransformacaoMateriaisRequestDto transformacaoMateriaisRequestDto) {
        TransformacaoMaterial transformacaoMaterial = new TransformacaoMaterial();
        transformacaoMaterial.setDtTransformacaoMaterial(new LocalDate());
        transformacaoMaterial.setHrTransformacaoMaterial(new LocalTime());
        transformacaoMaterial.setDmSituacao(NormalCancelado.NORMAL);

        transformacaoMaterial.setEmpresa(domainQueryService.getEmpresa(transformacaoMateriaisRequestDto.getIdEmpresa()));
        transformacaoMaterial.setPed(domainQueryService.getPed(transformacaoMateriaisRequestDto.getNrPed()));
        transformacaoMaterial.setProgramacao(domainQueryService.getProgramacao(transformacaoMateriaisRequestDto.getIdProgramacao()));

        Optional.ofNullable(transformacaoMateriaisRequestDto.getOrigens()).ifPresent(origens ->
                origens.stream().forEach(transformacaoMateriaisOrigemResponseDto -> transformacaoMaterial.add(toTransformacaoMaterialOrigem(transformacaoMateriaisOrigemResponseDto)))
        );

        Optional.ofNullable(transformacaoMateriaisRequestDto.getDestinos()).ifPresent(destinos ->
                destinos.stream().forEach(transformacaoMateriaisDestinoResponseDto -> transformacaoMaterial.add(toTransformacaoMaterialDestino(transformacaoMateriaisDestinoResponseDto)))
        );

        return transformacaoMaterial;
    }

    private TransformacaoMaterialOrigem toTransformacaoMaterialOrigem(TransformacaoMateriaisOrigemRequestDto origemDto) {
        TransformacaoMaterialOrigem transformacaoMaterialOrigem = new TransformacaoMaterialOrigem();
        transformacaoMaterialOrigem.setQuantidadeSaldoA(0D);
        transformacaoMaterialOrigem.setQuantidadeSaldoB(0D);

        Optional.ofNullable(origemDto.getLote()).ifPresent(lote -> {
            Lote loadedLote = domainQueryService.getLoteByNumero(origemDto.getLote().getNumero());
            transformacaoMaterialOrigem.setLote(loadedLote);

            LoteFilter loteFilter = origemDto.getLote().toLoteFilter(loadedLote);
            transformacaoMaterialOrigem.setLoteFilter(loteFilter);
        });

        transformacaoMaterialOrigem.setProduto(domainQueryService.getProduto(origemDto.getCdProduto()));

        transformacaoMaterialOrigem.setOperacaoEstoqueSaida(domainQueryService.getOperacaoEstoque(origemDto.getCdOperacaoEstoqueSaida()));
        transformacaoMaterialOrigem.setLocalizacaoEstoqueSaida(domainQueryService.getLocalizacaoEstoque(origemDto.getCdLocalizacaoEstoqueSaida()));

        transformacaoMaterialOrigem.setQuantidadeA(origemDto.getQuantidadeA());
        transformacaoMaterialOrigem.setQuantidadeB(origemDto.getQuantidadeB());

        return transformacaoMaterialOrigem;
    }

    private TransformacaoMaterialDestino toTransformacaoMaterialDestino(TransformacaoMateriaisDestinoRequestDto destinoDto) {
        TransformacaoMaterialDestino transformacaoMaterialDestino = new TransformacaoMaterialDestino();

        Optional.ofNullable(destinoDto.getLote()).ifPresent(lote -> {
            if (lote.getNumero() != null) {
                Lote loadedLote = domainQueryService.getLoteByNumero(lote.getNumero());
                transformacaoMaterialDestino.setLote(loadedLote);

                LoteFilter loteFilter = lote.toLoteFilter(loadedLote);
                transformacaoMaterialDestino.setLoteFilter(loteFilter);
            } else {
                LoteFilter loteFilter = lote.toLoteFilter();
                transformacaoMaterialDestino.setLoteFilter(loteFilter);
            }
        });

        transformacaoMaterialDestino.setProduto(domainQueryService.getProduto(destinoDto.getCdProduto()));

        transformacaoMaterialDestino.setOperacaoEstoqueEntrada(domainQueryService.getOperacaoEstoque(destinoDto.getCdOperacaoEstoqueEntrada()));
        transformacaoMaterialDestino.setLocalizacaoEstoqueEntrada(domainQueryService.getLocalizacaoEstoque(destinoDto.getCdLocalizacaoEstoqueEntrada()));

        transformacaoMaterialDestino.setQuantidadeA(destinoDto.getQuantidadeA());
        transformacaoMaterialDestino.setQuantidadeB(destinoDto.getQuantidadeB());
        return transformacaoMaterialDestino;
    }

    public TransformacaoMateriaisResponseDto toTransformacaoMateriaisResponseDto(TransformacaoMaterial transformacaoMaterial) {
        return TransformacaoMateriaisResponseDto.builder()
                .id(transformacaoMaterial.getIdTransformacaoMaterial())
                .situacao(transformacaoMaterial.getDmSituacao())
                .empresa(EmpresaDto.from(transformacaoMaterial.getEmpresa()))
                .ped(PedDto.from(transformacaoMaterial.getPed()))
                .programacao(ProgramacaoDto.from(transformacaoMaterial.getProgramacao()))
                .origens(transformacaoMaterial.getOrigens().stream().map(TransformacaoMateriaisOrigemResponseDto::from).collect(Collectors.toList()))
                .destinos(transformacaoMaterial.getDestinos().stream().map(TransformacaoMateriaisDestinoResponseDto::from).collect(Collectors.toList()))
                .build();
    }


}
