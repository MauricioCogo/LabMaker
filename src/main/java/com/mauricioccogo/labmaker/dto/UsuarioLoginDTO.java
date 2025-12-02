package com.mauricioccogo.labmaker.dto;

import com.mauricioccogo.labmaker.entity.Usuario;

public record UsuarioLoginDTO (
    String email,
    String senha
){
    public static Usuario toEntity(UsuarioLoginDTO dto){
        Usuario u = new Usuario();
        u.setEmail(dto.email);
        u.setSenha(dto.senha);
        return u;
    }
}
