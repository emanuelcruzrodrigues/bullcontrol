package br.com.bullcontrol.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "bullcontrol-api")
public class BullcontrolApiConfiguration {
    private String username;
    private BullcontrolConfiguration bullcontrol;
    private LocaleConfiguration locale;

    @Data
    public static class BullcontrolConfiguration {
        private String url;
    }

    @Data
    public static class LocaleConfiguration {
        private String language;
        private String country;
    }
}
