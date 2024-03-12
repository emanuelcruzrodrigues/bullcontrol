package br.com.bullcontrol.api.modulo.cadastro.service;

import br.com.bullcontrol.api.modulo.cadastro.domain.LoteDto;
import br.com.bullcontrol.api.exception.BullcontrolApiException;
import br.com.bullcontrol.api.invoker.BullcontrolService;
import br.com.bullcontrol.api.invoker.BullcontrolServicesInitializer;
import com.bullcontrol.config.database.DAO;
import com.bullcontrol.estoque.dao.LocalizacaoEstoqueDAO;
import com.bullcontrol.estoque.dao.OperacaoEstoqueDAO;
import com.bullcontrol.estoque.domain.*;
import com.bullcontrol.estoque.service.LocalizacaoEstoqueService;
import com.bullcontrol.estoque.service.LoteService;
import com.bullcontrol.estoque.service.OperacaoEstoqueService;
import com.bullcontrol.estoque.service.ProdutoService;
import com.bullcontrol.geral.domain.Empresa;
import com.bullcontrol.geral.service.EmpresaService;
import com.bullcontrol.pcp.domain.Programacao;
import com.bullcontrol.pcp.service.ProgramacaoService;
import com.bullcontrol.ped.domain.PED;
import com.bullcontrol.ped.service.PEDService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service
public class DomainQueryService {

    @BullcontrolService
    private EmpresaService empresaService;

    @BullcontrolService
    private DAO dao;

    @BullcontrolService
    private PEDService pedService;

    @BullcontrolService
    private ProgramacaoService programacaoService;

    @BullcontrolService
    private OperacaoEstoqueDAO operacaoEstoqueDAO;

    @BullcontrolService
    private OperacaoEstoqueService operacaoEstoqueService;

    @BullcontrolService
    private LocalizacaoEstoqueDAO localizacaoEstoqueDAO;

    @BullcontrolService
    private LocalizacaoEstoqueService localizacaoEstoqueService;

    @BullcontrolService
    private LoteService loteService;

    @BullcontrolService
    private ProdutoService produtoService;

    public DomainQueryService() {
        BullcontrolServicesInitializer.injectResources(this);
    }

    public Empresa getEmpresa(Integer idEmpresa) {
        if (idEmpresa == null) return null;

        Empresa empresa = empresaService.get(idEmpresa);
        if (empresa == null) throw BullcontrolApiException.builder()
                .httpStatusCode(HttpStatus.BAD_REQUEST)
                .message(String.format("Empresa nao localizada com o id: %d", idEmpresa))
                .build();
        return empresa;
    }

    public PED getPed(String nrPed) {
        if (nrPed == null) return null;

        Integer id = dao.uniqueResult("select p.id from PED p where p.cdPED = ?", nrPed);

        if (id == null) throw BullcontrolApiException.builder()
                .httpStatusCode(HttpStatus.BAD_REQUEST)
                .message(String.format("P&D nao localizado com o nr: %s", nrPed))
                .build();

        PED ped = pedService.get(id, true);
        return ped;
    }

    public Programacao getProgramacao(Integer idProgramacao) {
        if (idProgramacao == null) return null;

        Programacao programacao = programacaoService.get(idProgramacao, true);
        if (programacao == null) throw BullcontrolApiException.builder()
                .httpStatusCode(HttpStatus.BAD_REQUEST)
                .message(String.format("Programacao nao localizada com o id: %d", idProgramacao))
                .build();
        return programacao;
    }

    public OperacaoEstoque getOperacaoEstoque(String cdOperacaoEstoque) {
        if (cdOperacaoEstoque == null) return null;

        OperacaoEstoque example = new OperacaoEstoque();
        example.setCdOperacaoEstoque(cdOperacaoEstoque);
        Serializable id = operacaoEstoqueDAO.queryUniqueKeyId(example);
        if (id == null) throw BullcontrolApiException.builder()
                .httpStatusCode(HttpStatus.BAD_REQUEST)
                .message(String.format("Operacao estoque nao localizada com o codigo: %s", cdOperacaoEstoque))
                .build();
        return operacaoEstoqueService.get(id, true);
    }

    public LocalizacaoEstoque getLocalizacaoEstoque(String cdLocalizacaoEstoque) {
        if (cdLocalizacaoEstoque == null) return null;

        LocalizacaoEstoque example = new LocalizacaoEstoque();
        example.setCdLocalizacaoEstoque(cdLocalizacaoEstoque);
        Integer id = (Integer) localizacaoEstoqueDAO.queryUniqueKeyId(example);
        if (id == null) throw BullcontrolApiException.builder()
                .httpStatusCode(HttpStatus.BAD_REQUEST)
                .message(String.format("Localizacao estoque nao localizada com o codigo: %s", cdLocalizacaoEstoque))
                .build();
        return localizacaoEstoqueService.get(id, true);
    }

    public Lote getLote(Long id) {
        if (id == null) return null;
        return loteService.get(id);
    }

    public Lote getLote(LoteDto loteDto) {
        if (loteDto == null || loteDto.getNumero() == null) return null;

        List<LoteView> loteViews = loteService.get(loteDto.getNumero());

        if (loteViews.size() != 1) {
            throw BullcontrolApiException.builder()
                    .httpStatusCode(HttpStatus.BAD_REQUEST)
                    .message(String.format("Lote nao localizado com o numero: %s", loteDto.getNumero()))
                    .build();
        }

        return loteService.get(loteViews.get(0).getIdLote());
    }

    public Produto getProduto(String cdProduto) {
        if (cdProduto == null) return null;

        Produto produto = produtoService.getByCdProduto(cdProduto, true);
        if (produto == null) throw BullcontrolApiException.builder()
                .httpStatusCode(HttpStatus.BAD_REQUEST)
                .message(String.format("Produto nao localizada com o codigo: %s", cdProduto))
                .build();
        return produto;
    }
}
