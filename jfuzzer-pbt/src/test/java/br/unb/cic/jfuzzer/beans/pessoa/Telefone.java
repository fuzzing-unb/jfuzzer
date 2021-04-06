package br.unb.cic.jfuzzer.beans.pessoa;

import br.unb.cic.jfuzzer.beans.Entidade;

import lombok.Data;

@Data
public class Telefone implements Entidade<Integer> {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String numero;
    private String label;

    @Override
    public String toString() {
        return String.format("%s", numero);
    }

}
