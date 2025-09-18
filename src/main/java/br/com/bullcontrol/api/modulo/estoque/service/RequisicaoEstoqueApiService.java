package br.com.bullcontrol.api.modulo.estoque.service;

import br.com.bullcontrol.api.modulo.estoque.domain.request.RequisicaoEstoqueRequestDto;
import br.com.bullcontrol.api.modulo.estoque.domain.response.RequisicaoEstoqueResponseDto;
import br.com.bullcontrol.api.modulo.estoque.transformer.RequisicaoEstoqueTransformer;
import br.com.bullcontrol.api.exception.BullcontrolApiException;
import br.com.bullcontrol.api.invoker.BullcontrolService;
import br.com.bullcontrol.api.invoker.BullcontrolServicesInitializer;
import com.bullcontrol.estoque.domain.RequisicaoEstoque;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RequisicaoEstoqueApiService {

    @BullcontrolService
    private com.bullcontrol.estoque.service.RequisicaoEstoqueService requisicaoEstoqueService;

    @Autowired
    private RequisicaoEstoqueTransformer requisicaoEstoqueTransformer;

    public RequisicaoEstoqueApiService() {
        BullcontrolServicesInitializer.injectResources(this);
    }

    public RequisicaoEstoqueResponseDto save(RequisicaoEstoqueRequestDto requisicaoEstoqueRequestDto) {
        RequisicaoEstoque requisicaoEstoque = requisicaoEstoqueTransformer.toRequisicaoEstoque(requisicaoEstoqueRequestDto);
        RequisicaoEstoque saved = requisicaoEstoqueService.save(requisicaoEstoque);
        return requisicaoEstoqueTransformer.toRequisicaoEstoqueResponseDto(saved);
    }

    public RequisicaoEstoqueResponseDto delete(Integer id) {
        RequisicaoEstoque requisicaoEstoque = null;

        try {
            requisicaoEstoque = requisicaoEstoqueService.get(id);
        } catch (Exception e) {
            log.error("Ocorreu um erro no servidor ao tentar buscar pela requisicao {}", id);
        }
        if (requisicaoEstoque == null) {
            throw BullcontrolApiException.builder()
                    .httpStatusCode(HttpStatus.BAD_REQUEST)
                    .message(String.format("Requisicao de estoque nao localizada com o id: %d", id))
                    .build();
        }

        requisicaoEstoqueService.delete(requisicaoEstoque);

        return requisicaoEstoqueTransformer.toRequisicaoEstoqueResponseDto(requisicaoEstoqueService.get(id));
    }
}
