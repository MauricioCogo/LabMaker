package com.mauricioccogo.labmaker.dto;

import com.mauricioccogo.labmaker.entity.Usuario;
import com.mauricioccogo.labmaker.entity.Usuario.TipoUsuario;

public record UsuarioCreateDTO (
    String nome,
    String email,
    String senha,
    TipoUsuario tipo
){
    public static Usuario toEntity(UsuarioCreateDTO dto){
        Usuario u = new Usuario();
        u.setNome(dto.nome);
        u.setEmail(dto.email);
        u.setSenha(dto.senha);
        u.setTipo(dto.tipo);
        return u;
    }
}
