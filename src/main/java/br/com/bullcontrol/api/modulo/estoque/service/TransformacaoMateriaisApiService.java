package br.com.bullcontrol.api.modulo.estoque.service;

import br.com.bullcontrol.api.modulo.estoque.domain.request.TransformacaoMateriaisRequestDto;
import br.com.bullcontrol.api.modulo.estoque.domain.response.TransformacaoMateriaisResponseDto;
import br.com.bullcontrol.api.modulo.estoque.transformer.TransformacaoMateriaisTransformer;
import br.com.bullcontrol.api.exception.BullcontrolApiException;
import br.com.bullcontrol.api.invoker.BullcontrolService;
import br.com.bullcontrol.api.invoker.BullcontrolServicesInitializer;
import com.bullcontrol.estoque.domain.TransformacaoMaterial;
import com.bullcontrol.estoque.service.TransformacaoMaterialService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TransformacaoMateriaisApiService {
    @BullcontrolService
    private TransformacaoMaterialService transformacaoMaterialService;

    @Autowired
    private TransformacaoMateriaisTransformer transformacaoMateriaisTransformer;

    public TransformacaoMateriaisApiService() {
        BullcontrolServicesInitializer.injectResources(this);
    }

    public TransformacaoMateriaisResponseDto save(TransformacaoMateriaisRequestDto transformacaoMateriaisRequestDto) {
        TransformacaoMaterial transformacaoMaterial = transformacaoMateriaisTransformer.toTransformacaoMaterial(transformacaoMateriaisRequestDto);
        TransformacaoMaterial saved = transformacaoMaterialService.save(transformacaoMaterial);
        return transformacaoMateriaisTransformer.toTransformacaoMateriaisResponseDto(saved);
    }

    public TransformacaoMateriaisResponseDto delete(Integer id) {
        TransformacaoMaterial transformacaoMaterial = null;

        try {
            transformacaoMaterial = transformacaoMaterialService.get(id);
        } catch (Exception e) {
            log.error("Ocorreu um erro no servidor ao tentar buscar pela transformacao de materiais {}", id);
        }

        if (transformacaoMaterial == null) {
            throw BullcontrolApiException.builder()
                    .httpStatusCode(HttpStatus.BAD_REQUEST)
                    .message(String.format("Transformacao de materiais nao localizada com o id: %d", id))
                    .build();
        }

        transformacaoMaterialService.delete(transformacaoMaterial);

        return transformacaoMateriaisTransformer.toTransformacaoMateriaisResponseDto(transformacaoMaterialService.get(id));
    }
}
