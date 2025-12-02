package com.mauricioccogo.labmaker.dto;

import com.mauricioccogo.labmaker.entity.Requisicao;
import com.mauricioccogo.labmaker.entity.Requisicao.Status;

public record RequisicaoCreateDTO(
        Long usuarioId,
        String url,
        String descricao,
        Double qntdFilamento,
        Integer tempoEstimado,
        String tipoMaterial,
        Status status,
        Integer prioridade
) {
    public static Requisicao toEntity(RequisicaoCreateDTO dto) {
        Requisicao r = new Requisicao();
        r.setUrl(dto.url);
        r.setDescricao(dto.descricao);
        r.setQuantidadeFilamento(dto.qntdFilamento);
        r.setTempoEstimado(dto.tempoEstimado);
        r.setTipoMaterial(dto.tipoMaterial);
        r.setStatus(dto.status);
        r.setPrioridade(dto.prioridade);
        return r;
    }
}
