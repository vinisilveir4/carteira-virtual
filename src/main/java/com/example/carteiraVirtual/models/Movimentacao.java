package com.example.carteiraVirtual.models;

import com.example.carteiraVirtual.util.TipoMovimentacao;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Table(name = "movimentacoes")
@Entity
public class Movimentacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "data_movimentacao", nullable = false)
    LocalDateTime data;

    @Column(nullable = false)
    BigDecimal valor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    TipoMovimentacao tipo;

    @ManyToOne
    @JoinColumn(name = "carteira_id", referencedColumnName = "id")
    Carteira carteira;

//    public Movimentacoes() {
//        data = LocalDateTime.now();
//    }
}
