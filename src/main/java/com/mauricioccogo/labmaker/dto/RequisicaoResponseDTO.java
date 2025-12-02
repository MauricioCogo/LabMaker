package com.mauricioccogo.labmaker.dto;

import java.time.LocalDateTime;

import com.mauricioccogo.labmaker.entity.Requisicao;
import com.mauricioccogo.labmaker.entity.Requisicao.Status;

public record RequisicaoResponseDTO(
        Long id,
        String url,
        String descricao,
        Double qntdFilamento,
        Integer tempoEstimado,
        Double precoEstimado,
        String tipoMaterial,
        Status status,
        LocalDateTime dataSolicitacao,
        Integer prioridade,
        UsuarioResponseDTO usuario) {

    public static RequisicaoResponseDTO toDTO(Requisicao r){


        return new RequisicaoResponseDTO(
        r.getId(),
        r.getUrl(),
        r.getDescricao(),
        r.getQuantidadeFilamento(),
        r.getTempoEstimado(), 
        r.getPrecoEstimado(),
        r.getTipoMaterial(),
        r.getStatus(),
        r.getDataSolicitacao(),
        r.getPrioridade(),
        UsuarioResponseDTO.toDto(r.getUsuario()));
    }

    public static Requisicao toEntity(RequisicaoResponseDTO dto){
        if (dto == null) {
            return null;
        }
        Requisicao r = new Requisicao();
        r.setId(dto.id);
        r.setUrl(dto.url);
        r.setDescricao(dto.descricao);
        r.setQuantidadeFilamento(dto.qntdFilamento);
        r.setTempoEstimado(dto.tempoEstimado);
        r.setPrecoEstimado(dto.precoEstimado);
        r.setTipoMaterial(dto.tipoMaterial);
        r.setStatus(dto.status);
        r.setDataSolicitacao(dto.dataSolicitacao);
        r.setPrioridade(dto.prioridade);
        r.setUsuario(UsuarioResponseDTO.toEntity(dto.usuario));
        return r;
    }
}
