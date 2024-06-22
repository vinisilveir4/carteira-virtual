package com.example.carteiraVirtual.controllers;

import com.example.carteiraVirtual.dtos.CarteiraDTO;
import com.example.carteiraVirtual.dtos.MovimentacaoDTO;
import com.example.carteiraVirtual.dtos.UsuarioDTO;
import com.example.carteiraVirtual.models.Movimentacao;
import com.example.carteiraVirtual.services.CarteiraService;
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
@RequestMapping("/carteira")
public class CarteiraController {
    @Autowired
    CarteiraService carteiraService;

    @RequestMapping("/{idUsuario}")
    public ResponseEntity<RespostaDefault<CarteiraDTO>> buscarCarteira(@PathVariable(name = "idUsuario") Long idUsuario) {
        CarteiraDTO carteiraD = carteiraService.buscar(idUsuario);

        if(Objects.nonNull(carteiraD)) {
            var response = new RespostaDefault<CarteiraDTO>();
            response.setResponse(carteiraD);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/transferencia/{idUsuario}")
    public ResponseEntity<RespostaDefault<MovimentacaoDTO>> movimentacao(@PathVariable(name = "idUsuario") Long idUsuario, @Valid @RequestBody MovimentacaoDTO body, BindingResult result) {
        var response = new RespostaDefault<MovimentacaoDTO>();

        if(result.hasErrors()) {
            List<String> erros = new ArrayList<>();
            result.getAllErrors().forEach(err -> erros.add(err.getDefaultMessage()));
            response.setErrors(erros);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        var movimentacao = carteiraService.acao(idUsuario, body);

        if(Objects.nonNull(movimentacao)) {
            if(movimentacao.getErrors() != null)
                return ResponseEntity.status(HttpStatus.CONFLICT).body(movimentacao);

            return ResponseEntity.status(HttpStatus.OK).body(movimentacao);
        }

        return ResponseEntity.notFound().build();
    }
}
