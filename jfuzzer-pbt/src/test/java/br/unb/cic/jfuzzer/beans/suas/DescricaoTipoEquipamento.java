package br.unb.cic.jfuzzer.beans.suas;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import lombok.Data;

/**
 * Classe de domínio referente à descrição do {@link TipoEquipamento}.
 *
 */
@Data
public class DescricaoTipoEquipamento implements Serializable {
    private static final long serialVersionUID = 1L;

    private String titulo;

    private List<String> paragrafos;

    private List<ItemDescricaoTipoEquipamento> saibaMais;

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
        if (!(obj instanceof DescricaoTipoEquipamento)) {
            return false;
        }
        DescricaoTipoEquipamento other = (DescricaoTipoEquipamento) obj;
        return Objects.equals(paragrafos, other.paragrafos) && Objects.equals(saibaMais, other.saibaMais)
                && Objects.equals(titulo, other.titulo);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return Objects.hash(paragrafos, saibaMais, titulo);
    }

    @Override
    public String toString() {
        return String.format("DescricaoTipoEquipamento [titulo=%s, paragrafos=%s, saibaMais=%s]", titulo, paragrafos, saibaMais);
    }

}
