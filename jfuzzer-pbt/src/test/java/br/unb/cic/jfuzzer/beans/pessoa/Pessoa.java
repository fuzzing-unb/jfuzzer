package br.unb.cic.jfuzzer.beans.pessoa;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.unb.cic.jfuzzer.beans.Entidade;

import lombok.Data;

@Data
public class Pessoa implements Entidade<Integer> {
    private static final long serialVersionUID = 1L;

    private static final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public static String teste = "AAAA";
    public static final String teste2 = "AAAA";

    private Integer id;
    private String nome;
    private Date nascimento;
    private Float peso;

    private List<Telefone> telefones;
//    private Sexo sexo;

    @Override
    public String toString() {
        return String.format("Pessoa [id=%s, nome=%s, nascimento=%s, peso=%5.2f, telefones=%s]", id, nome, dateFormat.format(nascimento), peso, telefones);
    }

}
