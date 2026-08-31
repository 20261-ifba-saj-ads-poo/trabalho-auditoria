package br.edu.ifba.saj.fwads.service;

import java.util.List;
import java.util.Objects;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.function.Function;

import br.edu.ifba.saj.fwads.exception.RegraDeNegocioException;
import br.edu.ifba.saj.fwads.model.AbstractModel;
import br.edu.ifba.saj.fwads.model.Usuario;
import br.edu.ifba.saj.fwads.repository.GenericDAO;
import br.edu.ifba.saj.fwads.repository.GenericDAOImpl;

public class GenericServiceImpl<T extends AbstractModel<ID>, ID> implements GenericService<T, ID> {

    private GenericDAO<T, ID> repository;

    private final Class<T> tipoClass;
    private final Class<ID> entityClass;
    private final List<Function<T, ?>> atributosObrigatorios;

    @SafeVarargs
    public GenericServiceImpl(Class<T> tipoClass, Class<ID> entityClass, Function<T, ?>... atributosObrigatorios) {
        this.tipoClass = tipoClass;
        this.entityClass = entityClass;
        this.atributosObrigatorios = Arrays.asList(atributosObrigatorios);
        this.repository = new GenericDAOImpl<T, ID>(this.tipoClass, this.entityClass);
    }

    public List<T> buscarTodos() {
        return repository.buscarTodos();
    }

    public ID salvar(T entity, Usuario user)  throws RegraDeNegocioException{
        return repository.salvar(entity,user);
    }

    public T buscarPorId(ID id) {
        return repository.buscarPorId(id);
    }

    public void atualizar(T entity, Usuario user)  throws RegraDeNegocioException{
        repository.atualizar(entity,user);
    }

    public void deletar(ID id, Usuario user) {
        repository.deletar(id,user);
    }

    public Long count() {
        return (long) repository.buscarTodos().size();
    }

    @SafeVarargs
    public final List<T> buscarPorParametros(Predicate<T>... predicados) {
        
       return repository.buscarPorParametros(predicados);
    }

    public void validar(T entity)  throws RegraDeNegocioException {
        if (atributosObrigatorios == null || atributosObrigatorios.isEmpty()) {
            //return true;
        }
        for (Function<T, ?> getter : atributosObrigatorios) {
            Object valor = getter.apply(entity);
            if (Objects.isNull(valor)) {
                throw new RegraDeNegocioException(getter.toString());
                //return false;
            }
            if (valor instanceof String && ((String) valor).trim().isEmpty()) {
                throw new RegraDeNegocioException(getter.toString());
                //return false;
            }
        }
        //return true;
    }

}