package com.br.soft.demosoft;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@OpenAPIDefinition(info=@Info(title="Controle de votações"))
@EnableFeignClients
public class DemoSoftApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoSoftApplication.class, args);
    }

}
