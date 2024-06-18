package com.example.carteiraVirtual.services;

import com.example.carteiraVirtual.controllers.CarteiraController;
import com.example.carteiraVirtual.dtos.UsuarioDTO;
import com.example.carteiraVirtual.models.Usuario;
import com.example.carteiraVirtual.repositories.UsuarioRepository;
import com.example.carteiraVirtual.util.CPFUtil;
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

    public UsuarioDTO criarUsuario(UsuarioDTO usuario) {
        boolean maiorDeIdade = (LocalDate.now().getYear() - usuario.getDataNascimento().getYear()) >= 18;

        String cpfValido = CPFUtil.validarCPF(usuario.getCpf())
                ? CPFUtil.formatarCPF(usuario.getCpf())
                : null;

        TipoPais pais = null;
        for (TipoPais paisInfo : TipoPais.values())
            if (paisInfo.getDescricao().equalsIgnoreCase(usuario.getPais())) {
                pais = paisInfo;
                break;
            }

        if (cpfValido != null && pais != null && maiorDeIdade) {
            var usuarioModel = new Usuario();
            usuarioModel.setNome(usuario.getNome());
            usuarioModel.setCpf(cpfValido);
            usuarioModel.setDataNascimento(usuario.getDataNascimento());
            usuarioModel.setPais(pais);

            Usuario usuarioCriado = usuarioRepository.save(usuarioModel);
            carteiraService.criarCarteira(usuarioCriado, pais.getMoeda());

            return converter(usuarioCriado);
        }

        throw new RuntimeException("erro ao criar usuário");
    }

    private UsuarioDTO converter(Usuario usuario) {
        var usuarioD = new UsuarioDTO();
        usuarioD.setId(usuario.getId());
        usuarioD.setNome(usuario.getNome());
        usuarioD.setCpf(usuario.getCpf());
        usuarioD.setDataNascimento(usuario.getDataNascimento());
        usuarioD.setPais(usuario.getPais().getDescricao());
        Link link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CarteiraController.class)
                .buscarCarteira(usuario.getId())).withRel("Carteira virtual");

        usuarioD.add(link);
        return usuarioD;
    }

}
