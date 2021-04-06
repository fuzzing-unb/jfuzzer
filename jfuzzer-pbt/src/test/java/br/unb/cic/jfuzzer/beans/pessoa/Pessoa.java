package br.unb.cic.jfuzzer.beans.pessoa;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.unb.cic.jfuzzer.beans.Entidade;

import lombok.Data;

@Data
public class Pessoa implements Entidade<Long> {
    private static final long serialVersionUID = 1L;

    private static final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public static String teste = "AAAA";
    public static final String teste2 = "AAAA";

    private Long id;
    private String nome;
    private Date nascimento;
    private Float peso;
    private Double saldo;
    private Integer filhos;

    private List<Telefone> telefones;
    private Sexo sexo;

    @Override
    public String toString() {
        return String.format("Pessoa [id=%s, nome=%s, nascimento=%s, filhos=%s, peso=%5.2f, saldo=%5.2f, sexo=%s, telefones=%s]", id, nome, dateFormat.format(nascimento), filhos, peso, saldo, sexo, telefones);
    }

}
