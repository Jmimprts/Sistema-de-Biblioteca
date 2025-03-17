package br.com.catolicapb.bd2projeto1.enums;

public enum StatusReserva {
    PENDENTE, //criada, mas nao confirmada
    CONFIRMADA, // confirmada, mas aguardando retirada
    CANCELADA, // cancelada pelo leitor ou pelo sistema
    EXPIRADA, // reserva nao retirada no prazo
    FINALIZADA // concluida (livro retirado ou devolvido)
}
