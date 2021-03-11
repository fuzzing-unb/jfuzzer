package br.unb.cic.jfuzzer.beans.localidade;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.unb.cic.jfuzzer.beans.Entidade;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe que representa um Município de acordo com o IBGE.
 * 
 */

@Data
@NoArgsConstructor
@Entity
@Table(name = "TB_MUNICIPIO")
public class Municipio implements Entidade<Long> {
    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @NotBlank
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Min(0)
    @Max(9999)
    @Column(name = "codigo", nullable = false, unique = true)
    private Long codigo;

    @NotBlank
    @Column(name = "codigo_sem_dv", nullable = false, unique = true)
    private String codigoSemDv;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uf")
    private UnidadeFederacao uf;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_microrregiao")
    private Microrregiao microrregiao;

    public Municipio(Long id, String nome, @NotNull Long codigo, UnidadeFederacao uf) {
        this.id = id;
        this.nome = nome;
        this.codigo = codigo;
        this.uf = uf;
        String str = codigo + "";
        this.codigoSemDv = str.substring(0, str.length() - 1);
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
        if (!(obj instanceof Municipio)) {
            return false;
        }
        Municipio other = (Municipio) obj;
        return Objects.equals(codigo, other.codigo) && Objects.equals(codigoSemDv, other.codigoSemDv) && Objects.equals(id, other.id) && Objects.equals(microrregiao, other.microrregiao) && Objects.equals(nome, other.nome)
                && Objects.equals(uf, other.uf);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return Objects.hash(codigo, codigoSemDv, id, microrregiao, nome, uf);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("Municipio [id=%s, nome=%s, codigo=%s, codigoSemDv=%s]", id, nome, codigo, codigoSemDv);
    }

}
