package com.example.carteiraVirtual.dtos;

import com.example.carteiraVirtual.util.TipoPais;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class UsuarioDTO extends RepresentationModel<UsuarioDTO> {
    Long id;

    @NotBlank(message = "Nome obrigatório")
    String nome;

    @NotBlank(message = "Cpf obrigatório")
    @CPF(message = "Cpf inválido")
    String cpf;

    @Past(message = "Data inválida")
    LocalDate dataNascimento;

    @NotBlank(message = "Pais obrigatório")
    String pais;
}
