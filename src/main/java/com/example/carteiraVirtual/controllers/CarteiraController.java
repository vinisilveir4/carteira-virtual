package com.example.carteiraVirtual.controllers;

import com.example.carteiraVirtual.dtos.CarteiraDTO;
import com.example.carteiraVirtual.dtos.MovimentacaoDTO;
import com.example.carteiraVirtual.models.Movimentacao;
import com.example.carteiraVirtual.services.CarteiraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/carteira")
public class CarteiraController {
    @Autowired
    CarteiraService carteiraService;

    @RequestMapping("/{idUsuario}")
    public ResponseEntity<CarteiraDTO> buscarCarteira(@PathVariable(name = "idUsuario") Long idUsuario) {
        return ResponseEntity.status(HttpStatus.OK).body(carteiraService.buscar(idUsuario));
    }

    @PostMapping("/transferencia/{idUsuario}")
    public ResponseEntity<MovimentacaoDTO> movimentacao(@PathVariable(name = "idUsuario") Long idUsuario, @RequestBody MovimentacaoDTO body) {
        MovimentacaoDTO movimentacao = carteiraService.acao(idUsuario, body);

        if(Objects.nonNull(movimentacao))
            return ResponseEntity.status(HttpStatus.OK).body(movimentacao);

        return ResponseEntity.notFound().build();
    }
}
