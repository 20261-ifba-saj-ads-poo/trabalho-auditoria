package br.edu.ifba.saj.fwads.service;

import java.util.UUID;

import br.edu.ifba.saj.fwads.model.Autor;

public class AutorService extends GenericServiceImpl<Autor, UUID> {
    public AutorService() {
        super(Autor.class, UUID.class, Autor::getNome, Autor::getCPF, Autor::getEmail);
    }
}
