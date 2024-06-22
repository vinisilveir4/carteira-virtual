package com.example.carteiraVirtual.services;

import com.example.carteiraVirtual.controllers.CarteiraController;
import com.example.carteiraVirtual.dtos.UsuarioDTO;
import com.example.carteiraVirtual.models.Usuario;
import com.example.carteiraVirtual.repositories.UsuarioRepository;
import com.example.carteiraVirtual.util.TipoPais;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    CarteiraService carteiraService;

    public UsuarioDTO buscar(Long id) {
        Optional<Usuario> usuarioO = usuarioRepository.findById(id);

        if (usuarioO.isPresent())
            return converter(usuarioO.get());

        return null;
    }

    public UsuarioDTO criarUsuario(UsuarioDTO body) {
        boolean maiorDeIdade = (LocalDate.now().getYear() - body.getDataNascimento().getYear()) >= 18;

        TipoPais pais = null;
        for (TipoPais paisInfo : TipoPais.values())
            if (paisInfo.getDescricao().equalsIgnoreCase(body.getPais())) {
                pais = paisInfo;
                break;
            }

        if (pais != null && maiorDeIdade) {
            var usuarioModel = new Usuario();
            usuarioModel.setNome(body.getNome());
            usuarioModel.setCpf(formatarCPF(body.getCpf()));
            usuarioModel.setDataNascimento(body.getDataNascimento());
            usuarioModel.setPais(pais);

            Usuario usuarioCriado = usuarioRepository.save(usuarioModel);
            carteiraService.criarCarteira(usuarioCriado, pais.getMoeda());
            return converter(usuarioCriado);
        }

        throw new RuntimeException("erro ao criar usuário");
    }

    public String formatarCPF(String cpf) {
        cpf = cpf.replaceAll("[^0-9]", "");
        return cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }

    private UsuarioDTO converter(Usuario usuario) {
        var usuarioDto = new UsuarioDTO();
        BeanUtils.copyProperties(usuario, usuarioDto);
        usuarioDto.setPais(usuario.getPais().getDescricao());
        Link link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CarteiraController.class)
                .buscarCarteira(usuario.getId())).withRel("Carteira virtual");

        usuarioDto.add(link);
        return usuarioDto;
    }

}
