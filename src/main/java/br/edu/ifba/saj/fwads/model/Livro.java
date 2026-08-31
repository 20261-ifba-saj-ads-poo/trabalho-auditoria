package br.edu.ifba.saj.fwads.model;

import java.util.UUID;

public class Livro extends AbstractModel<UUID> {
    private String titulo;
    private String subTitulo;
    private String ISBN;
    private Autor autor;

    public Livro() {
    }

    public Livro(String titulo, String subTitulo, String iSBN, Autor autor) {
        this.titulo = titulo;
        this.subTitulo = subTitulo;
        ISBN = iSBN;
        this.autor = autor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSubTitulo() {
        return subTitulo;
    }

    public void setSubTitulo(String subTitulo) {
        this.subTitulo = subTitulo;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String iSBN) {
        ISBN = iSBN;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return "Livro [titulo=" + titulo + ", subTitulo=" + subTitulo + ", ISBN=" + ISBN + ", autor=" + autor + "]";
    }
}
