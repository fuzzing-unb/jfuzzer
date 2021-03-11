package br.unb.cic.jfuzzer.beans;

import java.io.Serializable;

/**
 * Define um contrato padrão para todas as entidades.
 *
 * @param <ID> Tipo da chave primária (identificador) da entidade
 */
@FunctionalInterface
public interface Entidade<ID extends Serializable> extends Serializable {

    /**
     * Retorna a chave primária da entidade.
     *
     * @return Retorna o atributo id.
     */
    ID getId();

}
