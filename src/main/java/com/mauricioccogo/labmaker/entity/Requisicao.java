package com.mauricioccogo.labmaker.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Requisicao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = true)
    private Double quantidadeFilamento;
    
    @Column(nullable = true)
    private Integer tempoEstimado;
    
    @Column(nullable = true)
    private Double precoEstimado;
    
    @Column
    private String tipoMaterial;

    @Enumerated(EnumType.STRING)
    @Column
    private Status status = Status.AGUARDANDO_APROVACAO;

    @Column
    private LocalDateTime dataSolicitacao = LocalDateTime.now();

    @Column
    private Integer prioridade = 0;

    @Column
    private int posicao;

    public enum Status {
        PENDENTE, EM_IMPRESSAO, CONCLUIDA, CANCELADA, AGUARDANDO_APROVACAO
    }
}
