package com.mauricioccogo.labmaker.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nome;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = false, nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column
    private TipoUsuario tipo = TipoUsuario.ALUNO;

    public enum TipoUsuario {
        ALUNO, PROFESSOR, EXTERNO, ADMIN
    }
}
