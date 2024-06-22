package com.example.carteiraVirtual.dtos;

import com.example.carteiraVirtual.util.TipoMovimentacao;
import jakarta.persistence.Column;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    Long id;

    @NotNull(message = "Valor obrigatório")
    @DecimalMin(value = "0.0", inclusive = false)
    BigDecimal valor;

    LocalDateTime data;

    @NotNull(message = "Tipo obrigatório")
    TipoMovimentacao tipo;
}
