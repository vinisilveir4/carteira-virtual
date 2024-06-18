package com.example.carteiraVirtual.dtos;

import com.example.carteiraVirtual.models.Carteira;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class CarteiraDTO {
    String moeda;
    BigDecimal saldo;
    List<MovimentacaoDTO> movimentacoes;
}
