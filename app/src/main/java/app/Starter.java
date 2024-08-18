package app;

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
    private NettyServer server;

    public static void main(String[] args) {
        SpringApplication.run(Starter.class);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        server.start();
    }

}
