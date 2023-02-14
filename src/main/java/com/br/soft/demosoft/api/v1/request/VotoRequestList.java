package com.br.soft.demosoft.api.v1.request;

import com.br.soft.demosoft.model.VotoEnum;
import lombok.Data;

@Data
public class VotoRequestList {
    private String PerguntaId;
    private VotoEnum voto;

}
