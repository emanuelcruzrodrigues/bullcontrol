package br.com.bullcontrol.api.modulo.cadastro.controller;

import br.com.bullcontrol.api.modulo.cadastro.domain.*;
import br.com.bullcontrol.api.modulo.cadastro.service.DomainQueryService;
import br.com.bullcontrol.api.modulo.sistema.domain.SessaoUsuarioResponseDto;
import com.bullcontrol.estoque.domain.LocalizacaoEstoque;
import com.bullcontrol.estoque.domain.Lote;
import com.bullcontrol.estoque.domain.OperacaoEstoque;
import com.bullcontrol.estoque.domain.Produto;
import com.bullcontrol.geral.domain.Empresa;
import com.bullcontrol.pcp.domain.Programacao;
import com.bullcontrol.ped.domain.PED;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/cadastro/v1/")
@RequiredArgsConstructor
public class CadastroController {

    private final DomainQueryService domainQueryService;

    @RequestMapping(value = "empresa/{idEmpresa}", method = RequestMethod.GET)
    public EmpresaDto getEmpresa(@PathVariable @Parameter(name = "cdEmpresa", description = "Codigo da empresa", example = "1") Integer idEmpresa) {
        Empresa empresa = domainQueryService.getEmpresa(idEmpresa);
        return EmpresaDto.from(empresa);
    }

    @RequestMapping(value = "ped/{nrPed}", method = RequestMethod.GET)
    public PedDto getPed(@PathVariable @Parameter(name = "nrPed", description = "Numero do P&D", example = "T 2023/020") String nrPed) {
        PED ped = domainQueryService.getPed(nrPed.replace("_", " ").replace(".","/"));
        return PedDto.from(ped);
    }

    @RequestMapping(value = "programacao/{id}", method = RequestMethod.GET)
    public ProgramacaoDto getPed(@PathVariable @Parameter(name = "id", description = "Id da Programacao", example = "28359") Integer id) {
        Programacao programacao = domainQueryService.getProgramacao(id);
        return ProgramacaoDto.from(programacao);
    }

    @RequestMapping(value = "operacao-estoque/{cd}", method = RequestMethod.GET)
    public OperacaoEstoqueDto getOperacaoEstoque(@PathVariable @Parameter(name = "cd", description = "Codigo da operacao de estoque", example = "SP") String cd) {
        OperacaoEstoque operacaoEstoque = domainQueryService.getOperacaoEstoque(cd);
        return OperacaoEstoqueDto.from(operacaoEstoque);
    }

    @RequestMapping(value = "localizacao-estoque/{cd}", method = RequestMethod.GET)
    public LocalizacaoEstoqueDto getLocalizacaoEstoque(@PathVariable @Parameter(name = "cd", description = "Codigo da localizacao de estoque", example = "1") String cd) {
        LocalizacaoEstoque localizacaoEstoque = domainQueryService.getLocalizacaoEstoque(cd);
        return LocalizacaoEstoqueDto.from(localizacaoEstoque);
    }

    @RequestMapping(value = "lote/{nr}", method = RequestMethod.GET)
    public LoteDto getLote(@PathVariable @Parameter(name = "nr", description = "Numero do lote", example = "100 A") String nr) {
        Lote lote = domainQueryService.getLote(LoteDto.builder().numero(nr).build());
        return LoteDto.from(lote);
    }

    @RequestMapping(value = "produto/{cd}", method = RequestMethod.GET)
    public ProdutoDto getProduto(@PathVariable @Parameter(name = "cd", description = "Codigo do produto", example = "1000") String cd) {
        Produto produto = domainQueryService.getProduto(cd);
        return ProdutoDto.from(produto);
    }

}
