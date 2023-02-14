package com.br.soft.demosoft.api.v1.controller;

import com.br.soft.demosoft.api.v1.mapper.SessaoMapper;
import com.br.soft.demosoft.api.v1.request.SessaoRequest;
import com.br.soft.demosoft.api.v1.response.SessaoResponse;
import com.br.soft.demosoft.service.SessaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Sessoes")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController("v1/sesssao")
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class SessaoController {
    final SessaoService service;

    @Operation(summary = "Lista todas as sessoes cadastradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Retorna uma lista com as pautas e as perguntas cadastradas",
                    content = @Content)
    })
    @GetMapping("list")
    public ResponseEntity<List<SessaoResponse>> findAll() {
        var sessoes = service.findAll();
        log.info("Obtendo a lista de sessões. Total de {} localizadas.", sessoes.size());
        return ResponseEntity.ok(sessoes.stream()
                .map(SessaoMapper::map)
                .collect(Collectors.toList()));
    }

    @Operation(summary = "Retorna uma sessão através do seu Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Retorna uma pauta e as perguntas cadastradas",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Quando não localiza a pauta selecionada",
                    content = @Content)
    })

    @GetMapping("/{id}")
    public ResponseEntity<SessaoResponse> findByid(@PathVariable String id) {
        var response = service.findById(id);

        if (response.isPresent()) {
            var pauta = response.get();
            return ResponseEntity.ok(SessaoMapper.map(pauta));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .build();
    }

    @Operation(summary = "Cadastra uma nova Sessão ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Resposta de criado com sucesso",
                    content = @Content)

    })
    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody SessaoRequest dto) {
        service.save(dto.getPautaId(), dto.getLimite());
        log.info("cadastro efetuado com sucesso");

        return ResponseEntity.status(HttpStatus.CREATED)
                .build();

    }

    @Operation(summary = "Deleta uma sessão")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Quando consegue deletar",
                    content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "Quando não consegue deletar",
                    content = @Content)

    })
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.ok().build();

    }

}


