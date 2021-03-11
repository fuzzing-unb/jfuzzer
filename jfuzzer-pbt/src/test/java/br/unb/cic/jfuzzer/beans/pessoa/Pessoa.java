package br.unb.cic.jfuzzer.beans.pessoa;

import java.util.Date;

import br.unb.cic.jfuzzer.beans.Entidade;

import lombok.Data;

@Data
public class Pessoa implements Entidade<Integer> {
    private static final long serialVersionUID = 1L;

    public static String teste = "AAAA";
    public static final String teste2 = "AAAA";

    private Integer id;
    private String nome;
    private Date nascimento;
    private Float peso;

    // private List<Telefone> telefones;
    // private Sexo sexo;

}
