package br.edu.ifba.saj.fwads.repository;

import java.lang.reflect.Constructor;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class IdGenerator {

    // Contador atômico para IDs numéricos
    // Garante que a geração de IDs seja thread-safe (segura para uso em ambientes
    // multithread) e evita conflitos ao gerar IDs sequenciais.
    private static final AtomicLong contador = new AtomicLong(0);

    /**
     * Gera um novo ID com base no tipo genérico T.
     * Se T for Number, retorna um valor sequencial atômico.
     * Se T for UUID, gera um novo UUID.
     *
     * @param tipoClasse A classe do tipo genérico T.
     * @return Um novo ID do tipo T.
     */
    @SuppressWarnings("unchecked")
    public static <T> T gerarNovoId(Class<T> tipoClasse) {
        if (Number.class.isAssignableFrom(tipoClasse)) {
            // Se T for um subtipo de Number, usa reflexão para criar uma instância
            try {
                // Obtém o construtor que recebe um long como parâmetro
                Constructor<T> construtor = tipoClasse.getConstructor(long.class);
                // Cria uma nova instância usando o valor do contador atômico
                return construtor.newInstance(contador.incrementAndGet());
            } catch (Exception e) {
                throw new RuntimeException("Falha ao gerar ID para o tipo: " + tipoClasse.getSimpleName(), e);
            }
        } else if (tipoClasse == UUID.class) {
            // Se T for UUID, gera um novo UUID
            return (T) UUID.randomUUID();
        }

        throw new IllegalArgumentException("Tipo de ID não suportado: " + tipoClasse.getSimpleName());
    }
}
