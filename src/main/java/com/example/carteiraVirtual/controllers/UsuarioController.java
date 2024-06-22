package com.example.carteiraVirtual.controllers;

import com.example.carteiraVirtual.dtos.CarteiraDTO;
import com.example.carteiraVirtual.dtos.UsuarioDTO;
import com.example.carteiraVirtual.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/{id}")
    public ResponseEntity<RespostaDefault<UsuarioDTO>> buscarPorId(@Valid @PathVariable(value = "id") Long id) {
        UsuarioDTO usuarioDto = usuarioService.buscar(id);

        if(Objects.nonNull(usuarioDto)) {
            var response = new RespostaDefault<UsuarioDTO>();
            response.setResponse(usuarioDto);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/criar")
    public ResponseEntity<RespostaDefault<UsuarioDTO>> criar(@Valid @RequestBody UsuarioDTO body, BindingResult result) {
        final var response = new RespostaDefault<UsuarioDTO>();

        if(result.hasErrors()) {
            List<String> erros = new ArrayList<>();
            result.getAllErrors().forEach(err -> erros.add(err.getDefaultMessage()));
            response.setErrors(erros);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        var usuarioDto = usuarioService.criarUsuario(body);
        response.setResponse(usuarioDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
