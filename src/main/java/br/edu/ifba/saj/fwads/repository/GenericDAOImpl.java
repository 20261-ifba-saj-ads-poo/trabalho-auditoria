package br.edu.ifba.saj.fwads.repository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.edu.ifba.saj.fwads.model.AbstractModel;
import br.edu.ifba.saj.fwads.model.Usuario;

public class GenericDAOImpl<T extends AbstractModel<ID>, ID> implements GenericDAO<T, ID> {
    private Map<ID, T> bancoDeDados = new HashMap<>();
    private final Class<T> tipoClass;
    private final Class<ID> tipoIdClass;
    private final ObjectMapper mapper;
    private final File arquivo;

    public GenericDAOImpl(Class<T> tipoClass, Class<ID> tipoIdClass) {
        this.tipoClass = tipoClass;
        this.tipoIdClass = tipoIdClass;
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());
        this.mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        this.arquivo = new File(tipoClass.getSimpleName() + ".json");
        carregarDoArquivo();
    }

    private void carregarDoArquivo() {
        if (arquivo.exists()) {
            try {
                JavaType mapType = mapper.getTypeFactory().constructMapType(HashMap.class, tipoIdClass, tipoClass);
                bancoDeDados = mapper.readValue(arquivo, mapType);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void salvarNoArquivo() {
        try {
            mapper.writeValue(arquivo, bancoDeDados);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ID salvar(T entidade, Usuario u) {
        ID novoId = IdGenerator.gerarNovoId(tipoIdClass);
        entidade.setId(novoId);
        entidade.setCreatedAt(LocalDateTime.now());
        entidade.setCreatedBy(u);
        carregarDoArquivo();
        bancoDeDados.put(entidade.getId(), entidade);
        salvarNoArquivo();
        return novoId;
    }

    @Override
    public T buscarPorId(ID id) {
        carregarDoArquivo();
        return bancoDeDados.get(id);
    }

    @Override
    public void atualizar(T entidade, Usuario u) {
        entidade.setUpdatedAt(LocalDateTime.now());
        entidade.setUpdatedBy(u);
        carregarDoArquivo();
        bancoDeDados.put(entidade.getId(), entidade);
        salvarNoArquivo();
    }

    @Override
    public void deletar(ID id, Usuario u) {
        T entidade = buscarPorId(id);
        entidade.setDeletedAt(LocalDateTime.now());
        entidade.setDeletedBy(u);
        carregarDoArquivo();
        bancoDeDados.put(entidade.getId(), entidade);
        salvarNoArquivo();
    }

    @Override
    //TODO Filtar excluidos logicos
    public List<T> buscarTodos() {
        carregarDoArquivo();
        return List.copyOf(bancoDeDados.values());
    }

    @SafeVarargs
    public final List<T> buscarPorParametros(Predicate<T>... predicados) {
        
        Predicate<T> filtroCombinado = Arrays.stream(predicados)
                .reduce(Predicate::and)
                .orElse(t -> true);

        return buscarTodos().stream()
                .filter(filtroCombinado)
                .toList();
    }

}