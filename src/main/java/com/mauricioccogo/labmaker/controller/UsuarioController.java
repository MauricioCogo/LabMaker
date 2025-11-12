package com.mauricioccogo.labmaker.controller;

import com.mauricioccogo.labmaker.dto.UsuarioCreateDTO;
import com.mauricioccogo.labmaker.dto.UsuarioResponseDTO;
import com.mauricioccogo.labmaker.entity.Usuario;
import com.mauricioccogo.labmaker.service.UsuarioService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @GetMapping
    public List<UsuarioResponseDTO> listarTodos() {
        return service.listarTodos();
    }

    @PostMapping
    public UsuarioResponseDTO salvar(@RequestBody UsuarioCreateDTO usuario) {
        return service.salvar(usuario);
    }

    @PostMapping("/auth")
    public UsuarioResponseDTO logar(@RequestBody UsuarioCreateDTO usuario) {
        return service.logar(usuario);
    }

    @GetMapping("/{id}")
    public UsuarioResponseDTO buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}
