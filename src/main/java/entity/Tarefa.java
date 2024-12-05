package entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Data; // @Data inclui @Getter, @Setter, @EqualsAndHashCode, @ToString, e @NoArgsConstructor automaticamente.

@Data
@Entity
public class Tarefa {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 100)
    private String titulo;

    @Column(length = 500)
    private String descricao;

    @Column(nullable = false)
    private boolean finalizado;

    @Column(nullable = false)
    private LocalDate dataPrevistaFinalizacao;

    private LocalDate dataFinalizacao;

    // Lombok irá gerar automaticamente o método setTitulo() aqui, sem necessidade de escrevê-lo manualmente.
}
