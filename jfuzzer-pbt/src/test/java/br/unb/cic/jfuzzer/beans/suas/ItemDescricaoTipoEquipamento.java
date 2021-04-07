package br.unb.cic.jfuzzer.beans.suas;

import java.io.Serializable;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe de domínio referente à descrição de Itens do {@link TipoEquipamento}
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDescricaoTipoEquipamento implements Serializable {
    private static final long serialVersionUID = 1L;

    private String chave;

    private String valor;

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
        if (!(obj instanceof ItemDescricaoTipoEquipamento)) {
            return false;
        }
        ItemDescricaoTipoEquipamento other = (ItemDescricaoTipoEquipamento) obj;
        return Objects.equals(chave, other.chave) && Objects.equals(valor, other.valor);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return Objects.hash(chave, valor);
    }

    @Override
    public String toString() {
        return String.format("ItemDescricaoTipoEquipamento [chave=%s, valor=%s]", chave, valor);
    }

}
