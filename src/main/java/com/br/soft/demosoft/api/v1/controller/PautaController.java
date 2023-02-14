package com.br.soft.demosoft.api.v1.controller;

import com.br.soft.demosoft.api.v1.mapper.PautaMapper;
import com.br.soft.demosoft.api.v1.mapper.PerguntaMapper;
import com.br.soft.demosoft.api.v1.mapper.VotoMapper;
import com.br.soft.demosoft.api.v1.request.PautaRequest;
import com.br.soft.demosoft.api.v1.request.VotoRequest;
import com.br.soft.demosoft.api.v1.response.PautaResponse;
import com.br.soft.demosoft.model.Pauta;
import com.br.soft.demosoft.model.dto.ResultadoDto;
import com.br.soft.demosoft.repository.SessaoRepository;
import com.br.soft.demosoft.service.PautaService;
import com.br.soft.demosoft.service.VotoService;
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

@Tag(name = "Pautas")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("v1/pauta")
@RequiredArgsConstructor
@Slf4j

public class PautaController {
    final PautaService pautaService;
    final PautaMapper mapper;
    final VotoService votoService;
    final SessaoRepository sessaoRepository;
    final VotoMapper votoMapper;

    @Operation(summary = "Lista todas as pautas cadastradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Retorna uma lista com as pautas e as perguntas cadastradas",
                    content = @Content)
    })
    @GetMapping("list")
    public ResponseEntity<List<PautaResponse>> findAll() {
        var pautas = pautaService.findAll();
        log.info("Obtendo a lista de pautas. Total de {} localizadas.", pautas.size());
        return ResponseEntity.ok(pautas.stream()
                .map(mapper::mapToResponse)
                .collect(Collectors.toList()));


    }

    @Operation(summary = "Retorna a pauta através do seu Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Retorna uma pauta e as perguntas cadastradas",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Quando não localiza a pauta selecionada",
                    content = @Content)
    })

    @GetMapping("/{id}")
    public ResponseEntity<PautaResponse> findByid(@PathVariable String id) {
        var response = pautaService.findById(id);

        if (response.isPresent()) {
            var pauta = response.get();
            return ResponseEntity.ok(mapper.mapToResponse(pauta));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .build();
    }

    @Operation(summary = "Retorna o resultado da votacao")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Retorna o resultado ",
                    content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "Quando não localizou a sessao para a pauta informada",
                    content = @Content)
    })
    @GetMapping("/resultado/{patuaId}")
    public ResponseEntity<List<ResultadoDto>> findByResultado(@PathVariable String patuaId) {
        var sessao = sessaoRepository.findByPautaId(patuaId);
        var response = votoService.resultado(sessao.get().getId(), patuaId);

        return ResponseEntity.ok(response);

    }

    @Operation(summary = "Cadastra uma nova pauta ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Resposta de criado com sucesso",
                    content = @Content)

    })
    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody PautaRequest dto) {
        var pauta = mapper.mapFromRequest(dto);
        log.info("Efetuando o cadastro da pauta {}", dto);
        var perguntas = dto.getPerguntas()
                .stream()
                .map(PerguntaMapper::map)
                .collect(Collectors.toList());

        pautaService.save(pauta, perguntas);
        log.info("cadastro efetuado com sucesso");

        return ResponseEntity.status(HttpStatus.CREATED)
                .build();

    }

    @Operation(summary = "Realiza a votacao")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Retorna uma pauta e as perguntas cadastradas",
                    content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "erro de validação",
                    content = @Content)
    })
    @PostMapping("/{pautaId}/{cpf}")
    public ResponseEntity<?> votar(@RequestBody VotoRequest votoRequest, @PathVariable String pautaId, @PathVariable String cpf) {
        var listVotos = votoRequest.getPerguntas()
                .stream()
                .map(votoRequestList -> {
                    return votoMapper.map(votoRequestList, pautaId, votoRequestList.getPerguntaId(), cpf);
                }).collect(Collectors.toList());

        votoService.votar(listVotos);
        log.info("voto realizado com sucesso");

        return ResponseEntity.ok()
                .build();

    }


    @Operation(summary = "Atualiza uma pauta existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "retorna a pauta atualizada",
                    content = @Content)

    })

    @PutMapping("/{id}")
    public ResponseEntity<PautaResponse> atualizar(@PathVariable String id, @RequestBody PautaRequest dto) {
        var pauta = mapper.mapFromRequest(dto);
        pauta.setId(id);
        log.info("Efetuando a alteracao da pauta {}", pauta);
        var perguntas = dto.getPerguntas()
                .stream()
                .map(PerguntaMapper::map)
                .collect(Collectors.toList());

        Pauta pautaAtualizada = pautaService.update(pauta, perguntas);
        log.info("atualização de pauta realizada com sucesso");

        return ResponseEntity.ok(mapper.mapToResponse(pautaAtualizada));

    }

    @Operation(summary = "Delete uma pauta")
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
        pautaService.delete(id);
        return ResponseEntity.ok().build();

    }

}

