package com.mauricioccogo.labmaker.controller;

import com.mauricioccogo.labmaker.dto.RequisicaoCreateDTO;
import com.mauricioccogo.labmaker.dto.RequisicaoResponseDTO;
import com.mauricioccogo.labmaker.entity.Requisicao;
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

    @PostMapping
    public RequisicaoResponseDTO salvar(@RequestBody RequisicaoCreateDTO requisicao) {
        return service.salvar(requisicao);
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
