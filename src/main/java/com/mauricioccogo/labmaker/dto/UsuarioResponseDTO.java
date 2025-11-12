package com.mauricioccogo.labmaker.dto;

import com.mauricioccogo.labmaker.entity.Usuario;
import com.mauricioccogo.labmaker.entity.Usuario.TipoUsuario;

public record UsuarioResponseDTO(
        Long id,
        String nome,
        String email,
        TipoUsuario tipo) {

    public static UsuarioResponseDTO toDto(Usuario u){
            return new UsuarioResponseDTO(
                u.getId(),
                u.getNome(),
                u.getEmail(),
                u.getTipo());
        }

    public static Usuario toEntity(UsuarioResponseDTO dto){
        if (dto==null) {
            return null;
        }
        
        Usuario u = new Usuario();
        u.setId(dto.id);
        u.setNome(dto.nome);
        u.setEmail(dto.email);
        u.setTipo(dto.tipo);
        return u;
    }
}
