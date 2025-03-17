package br.com.catolicapb.bd2projeto1.entity;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Emprestimo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucao;

    @ManyToOne
    @JoinColumn(name = "leitor_id")
    private Leitor leitor;

    @ManyToOne
    @JoinColumn(name = "livro_id")
    private Livro livro;
}
