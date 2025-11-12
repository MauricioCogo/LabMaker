package com.mauricioccogo.labmaker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mauricioccogo.labmaker.entity.Requisicao;

public interface RequisicaoRepository extends JpaRepository<Requisicao, Long> {
}
