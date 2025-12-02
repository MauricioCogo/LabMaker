package com.mauricioccogo.labmaker.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mauricioccogo.labmaker.dto.RequisicaoCreateDTO;
import com.mauricioccogo.labmaker.dto.RequisicaoResponseDTO;
import com.mauricioccogo.labmaker.dto.UsuarioResponseDTO;
import com.mauricioccogo.labmaker.entity.Requisicao;
import com.mauricioccogo.labmaker.entity.Usuario;
import com.mauricioccogo.labmaker.repository.RequisicaoRepository;

@Service
public class RequisicaoService {

    private final RequisicaoRepository requisicaoRepository;
    private final UsuarioService usuarioService;

    public RequisicaoService(RequisicaoRepository requisicaoRepository, UsuarioService usuarioService) {
        this.requisicaoRepository = requisicaoRepository;
        this.usuarioService = usuarioService;
    }

    public List<RequisicaoResponseDTO> listarTodas() {
        return requisicaoRepository.findAll().stream().map(RequisicaoResponseDTO::toDTO).collect(Collectors.toList());
    }

    public List<RequisicaoResponseDTO> listarTodasPorUsuario() {
        return requisicaoRepository.findAll().stream().map(RequisicaoResponseDTO::toDTO).collect(Collectors.toList());
    }

    public RequisicaoResponseDTO salvar(RequisicaoCreateDTO dto) {
        Requisicao requisicao = RequisicaoCreateDTO.toEntity(dto);
        double preco = calcularPrecoIntermediario(dto);
        requisicao.setPrecoEstimado(preco);

        Usuario u = UsuarioResponseDTO.toEntity(usuarioService.buscarPorId(dto.usuarioId()));
        requisicao.setUsuario(u);

        switch (u.getTipo()) {
            case ALUNO:
                requisicao.setPrioridade(1);

                break;

            case PROFESSOR:
                requisicao.setPrioridade(2);
                break;

            case EXTERNO:
                requisicao.setPrioridade(0);
                break;
        }

        Requisicao savedr = requisicaoRepository.save(requisicao);
        return RequisicaoResponseDTO.toDTO(savedr);
    }

    public RequisicaoResponseDTO editar(Long id, RequisicaoCreateDTO dto) {
        Requisicao requisicao = requisicaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Requisição não encontrada"));

        requisicao.setUrl(dto.url());
        requisicao.setDescricao(dto.descricao());
        requisicao.setQuantidadeFilamento(dto.qntdFilamento());
        requisicao.setTempoEstimado(dto.tempoEstimado());
        requisicao.setTipoMaterial(dto.tipoMaterial());
        requisicao.setStatus(dto.status());
        requisicao.setPosicao(dto.posicao());


        double preco = calcularPrecoIntermediario(dto);
        requisicao.setPrecoEstimado(preco);

        Usuario u = UsuarioResponseDTO.toEntity(usuarioService.buscarPorId(dto.usuarioId()));
        requisicao.setUsuario(u);

        Requisicao saved = requisicaoRepository.save(requisicao);

        return RequisicaoResponseDTO.toDTO(saved);
    }

    public RequisicaoResponseDTO buscarPorId(Long id) {
        Requisicao r = requisicaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Requisição não encontrada"));
        return RequisicaoResponseDTO.toDTO(r);
    }

    public void deletar(Long id) {
        requisicaoRepository.deleteById(id);
    }

    public static double calcularPrecoIntermediario(RequisicaoCreateDTO dto) {
        // parâmetros fixos
        double precoPorKg = 100.0; // R$/kg
        double precoKWh = 0.72; // R$/kWh
        double potenciaWatts = 150.0; // W da impressora
        double custoFixoPorHora = 10.0; // R$/h

        // trata nulos
        Integer tempoEstimado = dto.tempoEstimado() != null ? dto.tempoEstimado() : 0;
        Double qntdFilamento = dto.qntdFilamento() != null ? dto.qntdFilamento() : 0.0;

        // conversões
        double horas = tempoEstimado / 60.0;

        // cálculos
        double custoFilamento = (qntdFilamento / 1000.0) * precoPorKg;
        double consumoKWh = horas * (potenciaWatts / 1000.0);
        double custoEnergia = consumoKWh * precoKWh;
        double custoFixoIntermediario = horas * (custoFixoPorHora / 2);

        // soma total
        double precoSugerido = custoFilamento + custoEnergia + custoFixoIntermediario;

        return Math.round(precoSugerido * 100.0) / 100.0;
    }
}
