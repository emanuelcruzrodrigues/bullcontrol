package br.com.bullcontrol.api.modulo.sistema.transformer;

import br.com.bullcontrol.api.modulo.sistema.domain.SessaoUsuarioResponseDto;
import br.com.bullcontrol.api.utils.DateUtils;
import com.bullcontrol.sistema.domain.SessaoUsuario;
import org.springframework.stereotype.Component;

@Component
public class SessaoUsuarioTransformer {

    public SessaoUsuarioResponseDto toSessaoUsuarioResponseDto(SessaoUsuario sessao) {
        return SessaoUsuarioResponseDto.builder()
                .idSessao(sessao.getIdSessao())
                .nmUsuario(sessao.getUsuario().getNmUsuario())
                .idEmpresa(sessao.getEmpresa().getIdEmpresa())
                .locale(sessao.getLocale().toString())
                .ip(sessao.getIp())
                .javaVersion(sessao.getJavaVersion())
                .os(sessao.getOs())
                .dtHrCriacao(DateUtils.toString(sessao.getDtHrCriacao()))
                .dtHrUltimaIteracao(DateUtils.toString(sessao.getDtHrUltimaIteracao()))
                .nmUltimaIteracao(sessao.getNmUltimaIteracao())
                .dmSituacao(sessao.getDmSituacao())
                .build();
    }
}
