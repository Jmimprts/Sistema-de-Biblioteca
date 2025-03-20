package br.com.catolicapb.bd2projeto1.entity;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo, autor;
    private int anoPublicacao;
    private int quantidadeDisponivel;

    @OneToMany(mappedBy = "livro")
    private List<Reserva> reservas;

    @OneToMany(mappedBy = "livro")
    private List<Emprestimo> emprestimos;

    @Override
    public String toString() {
        return this.titulo;
    }
}
