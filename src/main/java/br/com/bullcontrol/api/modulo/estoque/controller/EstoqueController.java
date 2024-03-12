package br.com.bullcontrol.api.modulo.estoque.controller;

import br.com.bullcontrol.api.modulo.estoque.domain.request.RequisicaoEstoqueRequestDto;
import br.com.bullcontrol.api.modulo.estoque.domain.response.RequisicaoEstoqueResponseDto;
import br.com.bullcontrol.api.modulo.estoque.domain.request.TransformacaoMateriaisRequestDto;
import br.com.bullcontrol.api.modulo.estoque.domain.response.TransformacaoMateriaisResponseDto;
import br.com.bullcontrol.api.modulo.estoque.service.RequisicaoEstoqueApiService;
import br.com.bullcontrol.api.modulo.estoque.service.TransformacaoMateriaisApiService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/estoque/v1/")
@RequiredArgsConstructor
public class EstoqueController {
    private final TransformacaoMateriaisApiService transformacaoMateriaisApiService;
    private final RequisicaoEstoqueApiService requisicaoEstoqueApiService;

    @RequestMapping(value = "transformacao-materiais", method = RequestMethod.POST)
    public TransformacaoMateriaisResponseDto saveTransformacaoMateriais(@RequestBody @Validated TransformacaoMateriaisRequestDto transformacaoMateriaisRequestDto) {
        return transformacaoMateriaisApiService.save(transformacaoMateriaisRequestDto);
    }

    @RequestMapping(value = "transformacao-materiais/{id}", method = RequestMethod.DELETE)
    public TransformacaoMateriaisResponseDto deleteTransformacaoMateriais(@PathVariable @Parameter(name = "id", example = "100") Integer id) {
        return transformacaoMateriaisApiService.delete(id);
    }

    @RequestMapping(value = "requisicao", method = RequestMethod.POST)
    public RequisicaoEstoqueResponseDto saveRequisicaoEstoque(@RequestBody @Validated RequisicaoEstoqueRequestDto requisicaoEstoqueRequestDto) {
        return requisicaoEstoqueApiService.save(requisicaoEstoqueRequestDto);
    }

    @RequestMapping(value = "requisicao/{id}", method = RequestMethod.DELETE)
    public RequisicaoEstoqueResponseDto deleteRequisicaoEstoque(@PathVariable @Parameter(name = "id", example = "100") Integer id) {
        return requisicaoEstoqueApiService.delete(id);
    }

}
