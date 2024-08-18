package app;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class Starter implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(Starter.class);

    @Resource
    private OpenTelemetry openTelemetry;

    public static void main(String[] args) {
        SpringApplication.run(Starter.class);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Tracer tracer = openTelemetry.getTracer("app-stater", "1.0.0");



        Span parentSpan = tracer.spanBuilder("parent").startSpan();

        Scope scope = parentSpan.makeCurrent();


        logger.info("starter run {}", Span.current().getSpanContext().getTraceId());

        scope.close();

        parentSpan.end();


    }

}
