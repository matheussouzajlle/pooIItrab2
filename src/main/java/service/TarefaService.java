package service;

import entity.Tarefa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.TarefaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    public List<Tarefa> obterTodasTarefas() {
        return tarefaRepository.findAll();
    }

    public Optional<Tarefa> obterTarefaPorId(Long id) {
        return tarefaRepository.findById(id);
    }

    @Transactional
    public Tarefa inserirTarefa(Tarefa tarefa) {
        validarTarefa(tarefa);
        return tarefaRepository.save(tarefa);
    }

    @Transactional
    public Tarefa atualizarTarefa(Long id, Tarefa novaTarefa) {
        Tarefa tarefaExistente = tarefaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tarefa não encontrada"));

        if (tarefaExistente.isFinalizado()) {
            throw new IllegalArgumentException("Tarefa finalizada não pode ser modificada");
        }

        validarTarefa(novaTarefa);
        tarefaExistente.setTitulo(novaTarefa.getTitulo());
        tarefaExistente.setDescricao(novaTarefa.getDescricao());
        tarefaExistente.setDataPrevistaFinalizacao(novaTarefa.getDataPrevistaFinalizacao());
        tarefaExistente.setFinalizado(novaTarefa.isFinalizado());
        tarefaExistente.setDataFinalizacao(novaTarefa.getDataFinalizacao());

        return tarefaRepository.save(tarefaExistente);
    }

    @Transactional
    public void deletarTarefa(Long id) {
        if (!tarefaRepository.existsById(id)) {
            throw new IllegalArgumentException("Tarefa não encontrada");
        }
        tarefaRepository.deleteById(id);
    }

    public List<Tarefa> obterTarefasNaoFinalizadas() {
        return tarefaRepository.findByFinalizadoFalse();
    }

    public List<Tarefa> obterTarefasFinalizadas() {
        return tarefaRepository.findByFinalizadoTrue();
    }

    public List<Tarefa> obterTarefasAtrasadas() {
        return tarefaRepository.findByFinalizadoFalseAndDataPrevistaFinalizacaoBefore(LocalDate.now());
    }

    private void validarTarefa(Tarefa tarefa) {
        if (tarefa.getTitulo() == null || tarefa.getTitulo().length() < 5) {
            throw new IllegalArgumentException("O título da tarefa deve conter pelo menos 5 caracteres");
        }
        if (tarefa.getDataPrevistaFinalizacao() == null) {
            throw new IllegalArgumentException("A data prevista de finalização é obrigatória");
        }
    }
}
