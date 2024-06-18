package com.example.carteiraVirtual.models;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Table(name = "carteira")
@Entity
public class Carteira {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String moeda;

    @Column(nullable = false)
    BigDecimal saldo;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    Usuario usuario;

    @OneToMany(mappedBy = "carteira")
    List<Movimentacao> movimentacoes;
}
