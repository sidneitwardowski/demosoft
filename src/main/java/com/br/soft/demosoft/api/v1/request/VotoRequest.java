package com.br.soft.demosoft.api.v1.request;

import lombok.Data;

import java.util.Set;

@Data
public class VotoRequest {
    private Set<VotoRequestList> perguntas;
}
