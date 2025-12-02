package com.mauricioccogo.labmaker.controller;

import com.mauricioccogo.labmaker.dto.RequisicaoCreateDTO;
import com.mauricioccogo.labmaker.dto.RequisicaoResponseDTO;
import com.mauricioccogo.labmaker.service.RequisicaoService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/requisicoes")
public class RequisicaoController {

    private final RequisicaoService service;

    public RequisicaoController(RequisicaoService service) {
        this.service = service;
    }

    @GetMapping
    public List<RequisicaoResponseDTO> listarTodas() {
        return service.listarTodas();
    }

    @GetMapping("/user/{id}")
    public List<RequisicaoResponseDTO> listarTodasPorUsuario(@PathVariable Long id) {
        List<RequisicaoResponseDTO> rs = service.listarTodasPorUsuario(id);
        return rs;
    }

    @PostMapping
    public RequisicaoResponseDTO salvar(@RequestBody RequisicaoCreateDTO requisicao) {
        return service.salvar(requisicao);
    }

    @PutMapping("/{id}")
    public RequisicaoResponseDTO editar(
            @PathVariable Long id,
            @RequestBody RequisicaoCreateDTO requisicaoDTO) {
        return service.editar(id, requisicaoDTO);
    }

    @GetMapping("/{id}")
    public RequisicaoResponseDTO buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}
