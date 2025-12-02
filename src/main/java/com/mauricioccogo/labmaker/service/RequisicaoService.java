package com.mauricioccogo.labmaker.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mauricioccogo.labmaker.dto.RequisicaoCreateDTO;
import com.mauricioccogo.labmaker.dto.RequisicaoResponseDTO;
import com.mauricioccogo.labmaker.dto.UsuarioResponseDTO;
import com.mauricioccogo.labmaker.entity.Requisicao;
import com.mauricioccogo.labmaker.entity.Usuario;
import com.mauricioccogo.labmaker.entity.Requisicao.Status;
import com.mauricioccogo.labmaker.entity.Usuario.TipoUsuario;
import com.mauricioccogo.labmaker.repository.RequisicaoRepository;

import jakarta.transaction.Transactional;
import jakarta.xml.bind.annotation.XmlElement.DEFAULT;

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

    public List<RequisicaoResponseDTO> listarTodasPorUsuario(Long id) {
        return requisicaoRepository.findByUsuarioIdOrderByPosicao(id).stream().map(RequisicaoResponseDTO::toDTO)
                .collect(Collectors.toList());
    }

    public RequisicaoResponseDTO salvar(RequisicaoCreateDTO dto) {

        Requisicao requisicao = RequisicaoCreateDTO.toEntity(dto);

        double preco = calcularPrecoIntermediario(dto);
        requisicao.setPrecoEstimado(preco);
        requisicao.setStatus(Status.AGUARDANDO_APROVACAO);

        Usuario u = UsuarioResponseDTO.toEntity(usuarioService.buscarPorId(dto.usuarioId()));
        requisicao.setUsuario(u);

        // total de impressões já existentes
        long total = requisicaoRepository.count();

        // posição base
        int posicaoInicial = (int) total + 1;

        // bônus de prioridade
        int bonus = bonusPorTipo(u.getTipo());

        int posicaoFinal = Math.max(1, posicaoInicial - bonus);

        requisicao.setPosicao(posicaoFinal);

        ajustarFila(posicaoFinal);

        Requisicao saved = requisicaoRepository.save(requisicao);
        return RequisicaoResponseDTO.toDTO(saved);
    }

    public RequisicaoResponseDTO editar(Long id, RequisicaoCreateDTO dto) {
        Requisicao requisicao = requisicaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Requisição não encontrada"));

        requisicao.setUrl(dto.url());
        requisicao.setDescricao(dto.descricao());
        requisicao.setQuantidadeFilamento(dto.qntdFilamento());
        requisicao.setTempoEstimado(dto.tempoEstimado());
        requisicao.setTipoMaterial(dto.tipoMaterial());
        System.out.println(requisicao.getStatus() + " " + dto.status());
        requisicao.setStatus(dto.status());
        requisicao.setPrioridade(dto.prioridade());

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

    private int bonusPorTipo(TipoUsuario tipo) {
        return switch (tipo) {
            case PROFESSOR -> 4;
            case ALUNO -> 2;
            default -> 0;
        };
    }

    @Transactional
    public void ajustarFila(int posicaoInserida) {

        List<Requisicao> lista = requisicaoRepository
                .findAllByOrderByPosicaoAsc();

        for (Requisicao r : lista) {
            if (r.getPosicao() >= posicaoInserida) {
                r.setPosicao(r.getPosicao() + 1);
                requisicaoRepository.save(r);
            }
        }
    }

}
