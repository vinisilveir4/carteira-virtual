package com.example.carteiraVirtual.services;

import com.example.carteiraVirtual.controllers.CarteiraController;
import com.example.carteiraVirtual.controllers.RespostaDefault;
import com.example.carteiraVirtual.dtos.CarteiraDTO;
import com.example.carteiraVirtual.dtos.MovimentacaoDTO;
import com.example.carteiraVirtual.models.Carteira;
import com.example.carteiraVirtual.models.Movimentacao;
import com.example.carteiraVirtual.models.Usuario;
import com.example.carteiraVirtual.repositories.CarteiraRepository;
import com.example.carteiraVirtual.repositories.MovimentacaoRepository;
import com.example.carteiraVirtual.util.TipoMovimentacao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class CarteiraService {
    @Autowired
    CarteiraRepository carteiraRepository;

    @Autowired
    MovimentacaoRepository movimentacaoRepository;

    public CarteiraDTO buscar(Long idUsuario) {
        Optional<Carteira> carteiraO = carteiraRepository.findCarteiraBy(idUsuario);

        if (carteiraO.isPresent())
            return converter(carteiraO.get());

        return null;
    }

    public RespostaDefault<MovimentacaoDTO> acao(Long idUsuario, MovimentacaoDTO body) {
        Optional<Carteira> carteiraO = carteiraRepository.findCarteiraBy(idUsuario);

        if(carteiraO.isPresent()) {
            var response = new RespostaDefault<MovimentacaoDTO>();
            var carteira = carteiraO.get();

            boolean saldoSuficiente = carteira.getSaldo().compareTo(body.getValor()) >= 0; // true
            if (saldoSuficiente || body.getTipo().equals(TipoMovimentacao.DEPOSITO)) {
                var movimentacaoSalva = criarMovimentacao(body, carteira);
                body.setId(movimentacaoSalva.getId());
                body.setData(movimentacaoSalva.getData());
                Link link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CarteiraController.class)
                        .buscarCarteira(idUsuario)).withRel("Carteira virtual");
                body.add(link);

                response.setResponse(body);
                return response;
            }

            List<String> erros = Arrays.asList("Saldo insuficiente");
            response.setErrors(erros);
            return response;
        }

        return null;
    }

    public Movimentacao criarMovimentacao(MovimentacaoDTO movimentacaoDto, Carteira carteira) {
        var novaMovimentacao = new Movimentacao();
        novaMovimentacao.setTipo(movimentacaoDto.getTipo());
        novaMovimentacao.setValor(movimentacaoDto.getValor());
        novaMovimentacao.setCarteira(carteira);

        return movimentacaoRepository.save(novaMovimentacao);
    }

    public Carteira criarCarteira(Usuario usuario, String moeda) {
        var carteira = new Carteira();
        carteira.setMoeda(moeda);
        carteira.setSaldo(new BigDecimal(0));
        carteira.setUsuario(usuario);

        return carteiraRepository.save(carteira);
    }

    private CarteiraDTO converter(Carteira carteira) {
        var carteiraDto = new CarteiraDTO();
        BeanUtils.copyProperties(carteira, carteiraDto);

        List<MovimentacaoDTO> movimentacoes = new ArrayList<>();
        for(Movimentacao mov : carteira.getMovimentacoes()) {
            var movDTO = new MovimentacaoDTO();
            movDTO.removeLinks();
            BeanUtils.copyProperties(mov, movDTO);
            movimentacoes.add(movDTO);
        }

        carteiraDto.setMovimentacoes(movimentacoes);
        return carteiraDto;
    }
}
