package repository;

import entity.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    List<Tarefa> findByFinalizadoFalseAndDataPrevistaFinalizacaoBetween(LocalDate inicio, LocalDate fim);

    List<Tarefa> findByFinalizadoFalseAndDataPrevistaFinalizacaoBefore(LocalDate now);

    List<Tarefa> findByFinalizadoFalse();

    List<Tarefa> findByFinalizadoTrue();
}