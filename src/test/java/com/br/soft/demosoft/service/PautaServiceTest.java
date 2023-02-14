package com.br.soft.demosoft.service;

import com.br.soft.demosoft.Stub.PautaStub;
import com.br.soft.demosoft.Stub.PerguntaStub;
import com.br.soft.demosoft.model.Pauta;
import com.br.soft.demosoft.repository.PautaRepository;
import com.br.soft.demosoft.repository.PerguntaRepository;
import com.br.soft.demosoft.repository.SessaoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PautaServiceTest {

    @Mock
    private PautaRepository pautaRepository;
    @Mock
    private PerguntaRepository perguntaRepository;

    @Mock
    private SessaoRepository sessaoRepository;
    @Mock
    private Logger log;
    @InjectMocks
    private PautaService service;

    @Test
    void save() {
        when(pautaRepository.save(any())).thenReturn(PautaStub.getPauta());
        var pauta = PautaStub.getPauta();
        var perguntas = List.of(PerguntaStub.getPergunta(pauta));
        when(perguntaRepository.saveAll(any())).thenReturn(perguntas);
        when(pautaRepository.save(any())).thenReturn(pauta);
        service.save(pauta, perguntas);

        verify(pautaRepository, times(2)).save(any());
    }

    @Test
    void update() {
        when(pautaRepository.findById(anyString())).thenReturn(Optional.of(PautaStub.getPauta()));
        var pauta = pautaRepository.findById(anyString());
        when(perguntaRepository.findAllByPautaId(anyString())).thenReturn((List.of(PerguntaStub.getPergunta(pauta.get()))));
        var perguntas = perguntaRepository.findAllByPautaId(anyString());
        service.update(pauta.get(), perguntas);
        verify(perguntaRepository, times(1)).deleteAllByPautaId(anyString());

    }

    @Test
    void updateError(){
        when(pautaRepository.findById(anyString())).thenReturn(Optional.empty());
        var pauta = PautaStub.getPauta();
        var perguntas = List.of(PerguntaStub.getPergunta(pauta));
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
                () -> service.update(pauta, perguntas));
        assertEquals("Pauta não localizada.", illegalArgumentException.getMessage());
    }

    @Test
    void findAll() {
        when(service.findAll()).thenReturn(List.of(PautaStub.getPauta()));
        var result = service.findAll();
        assertTrue(result.size() > 0);
    }

    @Test
    void findById() {
        var mock = PautaStub.getPauta();
        mock.setId("123");
        when(pautaRepository.findById(anyString())).thenReturn(Optional.of(mock));
        var pauta = service.findById(mock.getId());

        assertTrue(pauta.isPresent());
    }

    @Test
    void delete() {
        when(pautaRepository.findById(anyString())).thenReturn(Optional.of(PautaStub.getPauta()));
        service.delete(anyString());
    }
}