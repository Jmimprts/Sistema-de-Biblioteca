package br.com.catolicapb.bd2projeto1.entity;
import br.com.catolicapb.bd2projeto1.enums.StatusReserva;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate dataReserva;

    @Enumerated(EnumType.STRING)
    private StatusReserva status = StatusReserva.ATIVA;

    @ManyToOne
    @JoinColumn(name = "livro_id")
    private Livro livro;

    @ManyToOne
    @JoinColumn(name = "leitor_id")
    private Leitor leitor;

    public List<StatusReserva> getProximosStatusValidos() {
        List<StatusReserva> proximosStatus = new ArrayList<>();
        if (this.status == StatusReserva.ATIVA) {
            proximosStatus.add(StatusReserva.CONCLUIDA);
            proximosStatus.add(StatusReserva.CANCELADA);
        }
        return proximosStatus;
    }
}
