package com.example.carteiraVirtual.dtos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class UsuarioDTO extends RepresentationModel<UsuarioDTO> {
    Long id;
    String nome;
    String cpf;
    LocalDate dataNascimento;
    String pais;
}
