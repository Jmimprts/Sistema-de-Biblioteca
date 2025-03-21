package br.com.catolicapb.bd2projeto1.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
public class Leitor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cpf;

    @OneToMany(mappedBy = "leitor")
    private List<Emprestimo> emprestimos;

    @OneToMany(mappedBy = "leitor")
    private List<Reserva> reservas;

    @Override
    public String toString() {
        return this.nome;
    }
}
