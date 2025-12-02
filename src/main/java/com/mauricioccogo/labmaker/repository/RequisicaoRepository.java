package com.mauricioccogo.labmaker.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mauricioccogo.labmaker.entity.Requisicao;

public interface RequisicaoRepository extends JpaRepository<Requisicao, Long> {

    List<Requisicao> findByUsuarioIdOrderByPosicao(Long usuarioId);

    @Query("SELECT COUNT(r) FROM Requisicao r WHERE r.prioridade = :prioridade")
    long countByPrioridade(int prioridade);

    List<Requisicao> findAllByOrderByPosicaoAsc();

}
