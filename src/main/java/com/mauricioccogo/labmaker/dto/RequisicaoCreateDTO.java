package com.mauricioccogo.labmaker.dto;

import com.mauricioccogo.labmaker.entity.Requisicao;

public record RequisicaoCreateDTO (
    Long usuarioId,
    Double qntdFilamento, //gramas
    Integer tempoEstimado, //minutos
    String tipoMaterial
){
    public static Requisicao toEntity(RequisicaoCreateDTO dto){
        Requisicao r = new Requisicao();
        r.setQuantidadeFilamento(dto.qntdFilamento);
        r.setTempoEstimado(dto.tempoEstimado);
        r.setTipoMaterial(dto.tipoMaterial);
        return r;
    }
}
