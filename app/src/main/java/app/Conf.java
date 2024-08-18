package app;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Conf {


    @Bean
    public OpenTelemetry openTelemetry() {

        return OpenTelemetrySdk.builder().buildAndRegisterGlobal();
    }
}
