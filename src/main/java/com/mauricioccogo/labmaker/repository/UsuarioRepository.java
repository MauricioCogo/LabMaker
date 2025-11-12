package com.mauricioccogo.labmaker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mauricioccogo.labmaker.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByEmail(String email);
}
