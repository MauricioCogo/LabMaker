package com.mauricioccogo.labmaker.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.mauricioccogo.labmaker.entity.Requisicao;

public interface RequisicaoRepository extends JpaRepository<Requisicao, Long> {

    List<Requisicao> findByUsuarioId(Long usuarioId);

}
