package br.com.bullcontrol.api.modulo.sistema.service;

import br.com.bullcontrol.api.config.BullcontrolApiConfiguration;
import br.com.bullcontrol.api.invoker.BullcontrolService;
import br.com.bullcontrol.api.invoker.BullcontrolServicesInitializer;
import com.bullcontrol.geral.domain.Empresa;
import com.bullcontrol.geral.service.EmpresaService;
import com.bullcontrol.sistema.domain.SessaoUsuario;
import com.bullcontrol.sistema.domain.Usuario;
import com.bullcontrol.sistema.service.SessaoUsuarioService;
import com.bullcontrol.sistema.service.UsuarioService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@Slf4j
public class SessaoUsuarioApi {

    @Autowired
    private BullcontrolApiConfiguration bullcontrolApiConfiguration;

    @BullcontrolService
    private SessaoUsuarioService sessaoUsuarioService;

    @BullcontrolService
    private UsuarioService usuarioService;

    @BullcontrolService
    private EmpresaService empresaService;

    private static SessaoUsuario sessaoUsuario;

    @PostConstruct
    public void start() {
        BullcontrolServicesInitializer.injectResources(this);

        final Usuario usuario = usuarioService.getByNmUsuario(bullcontrolApiConfiguration.getUsername());
        final Empresa empresa = empresaService.get(1);
        final String operationalSystem = System.getProperty("os.name") + " " + System.getProperty("os.arch");
        final String javaVersion = System.getProperty("java.version");

        final Locale locale = Locale.of(bullcontrolApiConfiguration.getLocale().getLanguage(), bullcontrolApiConfiguration.getLocale().getCountry());
        Locale.setDefault(locale);

        this.sessaoUsuario = sessaoUsuarioService.createSessao(usuario, empresa, locale, javaVersion, operationalSystem);

        log.info("Sessao iniciada: {}", sessaoUsuarioService.getSessaoAtivaFromUsuario(bullcontrolApiConfiguration.getUsername()).getIdSessao());
    }

    public static SessaoUsuario getSessaoUsuario() {
        return sessaoUsuario;
    }

}
