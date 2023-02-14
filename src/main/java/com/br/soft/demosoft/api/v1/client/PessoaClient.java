package com.br.soft.demosoft.api.v1.client;

import com.br.soft.demosoft.api.v1.client.response.PessoaResponse;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PessoaClient {
    private final PessoaService service;

    public Optional<PessoaResponse> verificaCpf(String cpf) {
        try {
            return service.verificaCpf(cpf);

        } catch (FeignException exception) {
            throw new RuntimeException(exception.getMessage());
        }

    }

    @FeignClient(url = "${demosoft.url-consulta-cpf}", name = "consulta-cpf")
    interface PessoaService {
        @GetMapping("{cpf}")
        Optional<PessoaResponse> verificaCpf(@PathVariable String cpf);

    }
}
