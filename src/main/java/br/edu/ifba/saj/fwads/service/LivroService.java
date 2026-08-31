package br.edu.ifba.saj.fwads.service;

import java.util.UUID;

import br.edu.ifba.saj.fwads.model.Livro;

public class LivroService extends GenericServiceImpl<Livro, UUID>  {
    public LivroService() {
        super(Livro.class, UUID.class, Livro::getTitulo, Livro::getSubTitulo, Livro::getISBN, Livro::getAutor);
    }
}
