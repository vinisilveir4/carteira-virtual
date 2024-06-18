package com.example.carteiraVirtual.services;

import com.example.carteiraVirtual.controllers.CarteiraController;
import com.example.carteiraVirtual.dtos.CarteiraDTO;
import com.example.carteiraVirtual.dtos.MovimentacaoDTO;
import com.example.carteiraVirtual.models.Carteira;
import com.example.carteiraVirtual.models.Movimentacao;
import com.example.carteiraVirtual.models.Usuario;
import com.example.carteiraVirtual.repositories.CarteiraRepository;
import com.example.carteiraVirtual.repositories.MovimentacaoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarteiraService {
    @Autowired
    CarteiraRepository carteiraRepository;

    @Autowired
    MovimentacaoRepository movimentacaoRepository;

    public CarteiraDTO buscar(Long idUsuario) {
        Optional<Carteira> carteira = carteiraRepository.findCarteiraBy(idUsuario);

        return converter(carteira.get());
    }

    public Carteira criarCarteira(Usuario usuario, String moeda) {
        var carteira = new Carteira();
        carteira.setMoeda(moeda);
        carteira.setSaldo(new BigDecimal(0));
        carteira.setUsuario(usuario);

        return carteiraRepository.save(carteira);
    }

    public MovimentacaoDTO acao(Long idUsuario, MovimentacaoDTO body) {
        Optional<Carteira> carteiraO = carteiraRepository.findCarteiraBy(idUsuario);

        if(carteiraO.isPresent()) {
            var carteira = carteiraO.get();
            if(carteira.getSaldo().compareTo(body.getValor()) != 0) {
                var movimentacao = new Movimentacao();
                movimentacao.setTipo(body.getTipo());
                movimentacao.setValor(body.getValor());
                movimentacao.setData(body.getData());
                movimentacao.setCarteira(carteira);
                movimentacaoRepository.save(movimentacao);

                Link link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CarteiraController.class)
                        .buscarCarteira(idUsuario)).withRel("Carteira virtual");
                body.add(link);
                return body;
            }

            throw new RuntimeException("Saldo Insuficiente");
        }

        return null;
    }

    private CarteiraDTO converter(Carteira carteira) {
        var carteiraD = new CarteiraDTO();
        BeanUtils.copyProperties(carteira, carteiraD);

        List<MovimentacaoDTO> movimentacoes = new ArrayList<>();
        for(Movimentacao mov : carteira.getMovimentacoes()) {
            var movDTO = new MovimentacaoDTO();
            BeanUtils.copyProperties(mov, movDTO);
            movimentacoes.add(movDTO);
        }

        carteiraD.setMovimentacoes(movimentacoes);
        return carteiraD;
    }
}
