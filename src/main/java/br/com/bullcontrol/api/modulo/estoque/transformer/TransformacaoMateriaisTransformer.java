package br.com.bullcontrol.api.modulo.estoque.transformer;

import br.com.bullcontrol.api.modulo.cadastro.domain.EmpresaDto;
import br.com.bullcontrol.api.modulo.cadastro.domain.PedDto;
import br.com.bullcontrol.api.modulo.cadastro.domain.ProgramacaoDto;
import br.com.bullcontrol.api.modulo.cadastro.service.DomainQueryService;
import br.com.bullcontrol.api.modulo.estoque.domain.TransformacaoMateriaisDestinoDto;
import br.com.bullcontrol.api.modulo.estoque.domain.TransformacaoMateriaisOrigemDto;
import br.com.bullcontrol.api.modulo.estoque.domain.TransformacaoMateriaisRequestDto;
import br.com.bullcontrol.api.modulo.estoque.domain.TransformacaoMateriaisResponseDto;
import com.bullcontrol.enums.NormalCancelado;
import com.bullcontrol.estoque.domain.*;
import lombok.RequiredArgsConstructor;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.springframework.stereotype.Component;

import java.util.Optional;

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
                origens.stream().forEach(transformacaoMateriaisOrigemDto -> transformacaoMaterial.add(toTransformacaoMaterialOrigem(transformacaoMateriaisOrigemDto)))
        );

        Optional.ofNullable(transformacaoMateriaisRequestDto.getDestinos()).ifPresent(destinos ->
                destinos.stream().forEach(transformacaoMateriaisDestinoDto -> transformacaoMaterial.add(toTransformacaoMaterialDestino(transformacaoMateriaisDestinoDto)))
        );

        return transformacaoMaterial;
    }

    private TransformacaoMaterialOrigem toTransformacaoMaterialOrigem(TransformacaoMateriaisOrigemDto transformacaoMateriaisOrigemDto) {
        TransformacaoMaterialOrigem transformacaoMaterialOrigem = new TransformacaoMaterialOrigem();
        transformacaoMaterialOrigem.setQuantidadeSaldoA(0D);
        transformacaoMaterialOrigem.setQuantidadeSaldoB(0D);

        Optional.ofNullable(transformacaoMateriaisOrigemDto.getLote()).ifPresent(lote -> {
            transformacaoMaterialOrigem.setLote(domainQueryService.getLote(transformacaoMateriaisOrigemDto.getLote()));
            transformacaoMaterialOrigem.setLoteFilter(transformacaoMateriaisOrigemDto.getLote().toLoteFilter());
        });

        transformacaoMaterialOrigem.setProduto(domainQueryService.getProduto(transformacaoMateriaisOrigemDto.getCdProduto()));

        transformacaoMaterialOrigem.setOperacaoEstoqueSaida(domainQueryService.getOperacaoEstoque(transformacaoMateriaisOrigemDto.getCdOperacaoEstoqueSaida()));
        transformacaoMaterialOrigem.setLocalizacaoEstoqueSaida(domainQueryService.getLocalizacaoEstoque(transformacaoMateriaisOrigemDto.getCdLocalizacaoEstoqueSaida()));

        transformacaoMaterialOrigem.setQuantidadeA(transformacaoMateriaisOrigemDto.getQuantidadeA());
        transformacaoMaterialOrigem.setQuantidadeB(transformacaoMateriaisOrigemDto.getQuantidadeB());

        return transformacaoMaterialOrigem;
    }

    private TransformacaoMaterialDestino toTransformacaoMaterialDestino(TransformacaoMateriaisDestinoDto destinoDto) {
        TransformacaoMaterialDestino transformacaoMaterialDestino = new TransformacaoMaterialDestino();

        Optional.ofNullable(destinoDto.getLote()).ifPresent(lote -> {
            transformacaoMaterialDestino.setLote(domainQueryService.getLote(destinoDto.getLote()));
            transformacaoMaterialDestino.setLoteFilter(destinoDto.getLote().toLoteFilter());
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
                .build();
    }


}
