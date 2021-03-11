package br.unb.cic.jfuzzer.beans.localidade;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.unb.cic.jfuzzer.beans.Entidade;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe que representa uma Unidade da Federação.
 * 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnidadeFederacao implements Entidade<Long> {
    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @NotBlank
    @Column(name = "nome", length = 255, nullable = false, unique = false, updatable = false)
    private String nome;

    @NotNull
    @Size(min = 2, max = 2)
    @Column(name = "sigla", length = 2, nullable = false, unique = true, updatable = false)
    private String sigla;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_regiao")
    private Regiao regiao;

    @OneToMany(mappedBy = "uf", fetch = FetchType.LAZY)
    private Set<Mesorregiao> mesorregioes = new HashSet<>();

    @OneToMany(mappedBy = "uf", fetch = FetchType.LAZY)
    private Set<Municipio> municipios = new HashSet<>();

    public UnidadeFederacao(Long id, String nome) {
        this(id, nome, "");
    }

    public UnidadeFederacao(Long id, String nome, String sigla) {
        this.id = id;
        this.nome = nome;
        this.sigla = sigla;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof UnidadeFederacao)) {
            return false;
        }
        UnidadeFederacao other = (UnidadeFederacao) obj;
        return Objects.equals(id, other.id) && Objects.equals(mesorregioes, other.mesorregioes) && Objects.equals(municipios, other.municipios) && Objects.equals(nome, other.nome) && Objects.equals(regiao, other.regiao)
                && Objects.equals(sigla, other.sigla);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, mesorregioes, municipios, nome, regiao, sigla);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("UnidadeFederacao [id=%s, nome=%s, sigla=%s]", id, nome, sigla);
    }

}
