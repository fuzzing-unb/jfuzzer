package br.unb.cic.jfuzzer.beans.localidade;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.unb.cic.jfuzzer.beans.Entidade;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe que representa uma Microrregião de acordo com o IBGE.
 * 
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TB_MICRORREGIAO")
public class Microrregiao implements Entidade<Long> {
    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "codigo")
    private Integer codigo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_mesorregiao")
    private Mesorregiao mesorregiao;

    @OneToMany(mappedBy = "microrregiao", fetch = FetchType.LAZY)
    private Set<Municipio> municipios = new HashSet<>();

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
        if (!(obj instanceof Microrregiao)) {
            return false;
        }
        Microrregiao other = (Microrregiao) obj;
        return Objects.equals(codigo, other.codigo) && Objects.equals(id, other.id) && Objects.equals(mesorregiao, other.mesorregiao) && Objects.equals(municipios, other.municipios) && Objects.equals(nome, other.nome);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return Objects.hash(codigo, id, mesorregiao, municipios, nome);
    }

}
