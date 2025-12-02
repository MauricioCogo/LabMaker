package com.mauricioccogo.labmaker.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mauricioccogo.labmaker.dto.UsuarioCreateDTO;
import com.mauricioccogo.labmaker.dto.UsuarioLoginDTO;
import com.mauricioccogo.labmaker.dto.UsuarioResponseDTO;
import com.mauricioccogo.labmaker.entity.Usuario;
import com.mauricioccogo.labmaker.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<UsuarioResponseDTO> listarTodos() {
        return usuarioRepository.findAll().stream().map(UsuarioResponseDTO::toDto).collect(Collectors.toList());
    }

    public UsuarioResponseDTO salvar(UsuarioCreateDTO usuario) {
        Usuario u = UsuarioCreateDTO.toEntity(usuario);
        Usuario savedU = usuarioRepository.save(u);
        return UsuarioResponseDTO.toDto(savedU);
    }

    public UsuarioResponseDTO logar(UsuarioLoginDTO usuario) {
        Usuario u = usuarioRepository.findByEmail(usuario.email());
        if (usuario.email().equals(u.getEmail()) && usuario.senha().equals(u.getSenha())) {
            return UsuarioResponseDTO.toDto(u);
        }
        return null;
    }

    public UsuarioResponseDTO buscarPorId(Long id) {
        Usuario u = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return UsuarioResponseDTO.toDto(u);
    }

    public void deletar(Long id) {
        usuarioRepository.deleteById(id);
    }
}
