package br.edu.ifba.saj.fwads.service;

import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.function.Predicate;

import br.edu.ifba.saj.fwads.exception.AutenticacaoInvalidaException;
import br.edu.ifba.saj.fwads.model.Usuario;

public class UsuarioService extends GenericServiceImpl<Usuario, UUID> {

    public UsuarioService() {
        super(Usuario.class, UUID.class, Usuario::getLogin, Usuario::getSenha, Usuario::getEmail);
    }

    public Usuario validaLogin(String login, String senha) throws AutenticacaoInvalidaException {
        try {
            Predicate<Usuario> predicateLogin = u -> java.util.Objects.equals(u.getLogin(), login);
            Predicate<Usuario> predicateSenha = u -> java.util.Objects.equals(u.getSenha(), senha);
            return buscarPorParametros(predicateLogin, predicateSenha).getFirst();
        } catch (NoSuchElementException e) {
            throw new AutenticacaoInvalidaException(
                    "Não foi possível localizar o usuário " + login + ", ou a senha esta errada");
        }
    }

}
