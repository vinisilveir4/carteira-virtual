package com.example.carteiraVirtual.controllers;

import com.example.carteiraVirtual.dtos.UsuarioDTO;
import com.example.carteiraVirtual.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> buscarPorId(@PathVariable(value = "id") Long id) {
        UsuarioDTO carteira = usuarioService.buscar(id);

        if(Objects.nonNull(carteira))
            return ResponseEntity.status(HttpStatus.OK).body(carteira);

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/criar")
    public ResponseEntity<UsuarioDTO> criar(@RequestBody UsuarioDTO usuario) {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.criarUsuario(usuario));
    }

}
