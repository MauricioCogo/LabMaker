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

    @Column
    private Double quantidadeFilamento;
    
    @Column
    private Integer tempoEstimado;
    
    @Column
    private Double precoEstimado;
    
    @Column
    private String tipoMaterial;

    @Enumerated(EnumType.STRING)
    @Column
    private Status status = Status.PENDENTE;

    @Column
    private LocalDateTime dataSolicitacao = LocalDateTime.now();

    @Column(nullable = true)
    private LocalDateTime dataPrevista;

    @Column
    private Integer prioridade = 0;

    public enum Status {
        PENDENTE, EM_IMPRESSAO, CONCLUIDA, CANCELADA
    }
}
