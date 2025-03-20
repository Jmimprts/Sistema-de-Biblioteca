package br.com.catolicapb.bd2projeto1.entity;
import br.com.catolicapb.bd2projeto1.enums.StatusEmprestimo;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Emprestimo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucao;

    @Enumerated(EnumType.STRING)
    private StatusEmprestimo status;

    @ManyToOne
    @JoinColumn(name = "leitor_id")
    private Leitor leitor;

    @ManyToOne
    @JoinColumn(name = "livro_id")
    private Livro livro;

    public List<StatusEmprestimo> getProximosStatusValidos() {
        List<StatusEmprestimo> proximosStatus = new ArrayList<>();
        if (this.status == StatusEmprestimo.PENDENTE) {
            proximosStatus.add(StatusEmprestimo.FINALIZADO);
        } else if (this.status == StatusEmprestimo.ATRASADO) {
            proximosStatus.add(StatusEmprestimo.FINALIZADO);
        }
        return proximosStatus;
    }
}
