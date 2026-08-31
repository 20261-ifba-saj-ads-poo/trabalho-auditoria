package br.edu.ifba.saj.fwads.service;

import java.util.List;

import br.edu.ifba.saj.fwads.exception.RegraDeNegocioException;
import br.edu.ifba.saj.fwads.model.AbstractModel;
import br.edu.ifba.saj.fwads.model.Usuario;

public interface GenericService<T extends AbstractModel<ID>, ID> {
    ID salvar(T entidade, Usuario u) throws RegraDeNegocioException;
    void atualizar(T entidade, Usuario u) throws RegraDeNegocioException;
    T buscarPorId(ID id);
    void deletar(ID id, Usuario u);
    List<T> buscarTodos();
    void validar(T entidade) throws RegraDeNegocioException;
}