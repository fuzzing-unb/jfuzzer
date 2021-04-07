package br.unb.cic.jfuzzer.beans.suas;

import java.util.Objects;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.unb.cic.jfuzzer.beans.Entidade;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe de domínio referente ao Tipo de Equipamento.
 */
@Data
@NoArgsConstructor
public class TipoEquipamento implements Entidade<String> {
    private static final long serialVersionUID = 1L;

    private String id;

    @NotEmpty
    @Size(max = 60)
    private String nome;

    @NotEmpty
    @Size(max = 60)
    private String sigla;

    @NotNull
    private DescricaoTipoEquipamento descricao;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TipoEquipamento other = (TipoEquipamento) obj;
        return Objects.equals(descricao, other.descricao) && Objects.equals(id, other.id) && Objects.equals(nome, other.nome) && Objects.equals(sigla, other.sigla);
    }

    @Override
    public int hashCode() {
        return Objects.hash(descricao, id, nome, sigla);
    }

    @Override
    public String toString() {
        return String.format("TipoEquipamento [id=%s, nome=%s, sigla=%s, descricao=%s]", id, nome, sigla, descricao);
    }

}
