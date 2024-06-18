package com.example.carteiraVirtual.dtos;

import com.example.carteiraVirtual.util.TipoMovimentacao;
import jakarta.persistence.Column;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class MovimentacaoDTO extends RepresentationModel<MovimentacaoDTO> {
    LocalDateTime data;
    BigDecimal valor;
    TipoMovimentacao tipo;
}
