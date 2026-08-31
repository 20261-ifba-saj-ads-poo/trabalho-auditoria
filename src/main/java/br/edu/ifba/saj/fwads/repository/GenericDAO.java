package br.edu.ifba.saj.fwads.repository;

import java.util.List;
import java.util.function.Predicate;

import br.edu.ifba.saj.fwads.model.AbstractModel;
import br.edu.ifba.saj.fwads.model.Usuario;

public interface GenericDAO<T extends AbstractModel<ID>, ID> {
    ID salvar(T entidade, Usuario u);
    void atualizar(T entidade, Usuario u);
    T buscarPorId(ID id);
    void deletar(ID id, Usuario u);
    List<T> buscarTodos();
    List<T> buscarPorParametros(Predicate<T>... predicados);
}