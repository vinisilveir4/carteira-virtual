package com.example.carteiraVirtual.models;
import com.example.carteiraVirtual.util.TipoPais;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Table(name = "usuario")
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String nome;

    @Column(length = 15, nullable = false, unique = true)
    String cpf;

    @Column(name = "data_nascimento", nullable = false)
    LocalDate dataNascimento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    TipoPais pais;

//    @OneToOne(mappedBy = "usuario")
//    Carteira carteira;
}
