package com.mauricioccogo.labmaker.dto;

import com.mauricioccogo.labmaker.entity.Requisicao;

public record RequisicaoCreateDTO (
    Long usuarioId,
    String url,
    String descricao,
    Double qntdFilamento,
    Integer tempoEstimado,
    String tipoMaterial
){
    public static Requisicao toEntity(RequisicaoCreateDTO dto){
        Requisicao r = new Requisicao();
        r.setUrl(dto.url);
        r.setDescricao(dto.descricao);
        r.setQuantidadeFilamento(dto.qntdFilamento);
        r.setTempoEstimado(dto.tempoEstimado);
        r.setTipoMaterial(dto.tipoMaterial);
        return r;
    }
}
