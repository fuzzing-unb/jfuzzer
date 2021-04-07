package br.unb.cic.jfuzzer.beans.suas;

import java.util.Objects;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.unb.cic.jfuzzer.beans.Entidade;

import lombok.Data;

/**
 * Classe de domínio referente ao Equipamento.
 */
@Data
public class Equipamento implements Entidade<String> {
    private static final long serialVersionUID = 1L;

    private String id;

    @NotNull
    @Size(max = 31)
    private String identificador;

    @NotBlank
    @Size(max = 60)
    private String nome;

    @Min(999999)
    @NotNull
    private Long codigoMunicipio;

    @NotBlank
    private String nomeMunicipio;

    @NotBlank
    private String unidadeFederacao;

    @NotBlank
    @Size(max = 60)
    private String logradouro;

    @NotBlank
    @Size(max = 200)
    private String endereco;

    @Size(max = 10)
    private String numero;

    @Size(max = 60)
    private String complemento;

    @Size(max = 80)
    private String bairro;

    @Size(max = 100)
    private String pontoDeReferencia;

    @Size(max = 40)
    private String telefone;

    @Size(max = 10)
    private String ramal;

    @Size(max = 90)
    // @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    @Email
    private String email;

    private Coordenada coordenada;

    @Size(max = 30)
    private String diasSemana;

    @Size(max = 30)
    private String horasDia;

    @NotNull
    private TipoEquipamento tipoEquipamento;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Equipamento other = (Equipamento) obj;
        return Objects.equals(bairro, other.bairro) && Objects.equals(codigoMunicipio, other.codigoMunicipio) && Objects.equals(complemento, other.complemento) && Objects.equals(coordenada, other.coordenada)
                && Objects.equals(diasSemana, other.diasSemana) && Objects.equals(email, other.email) && Objects.equals(endereco, other.endereco) && Objects.equals(horasDia, other.horasDia) && Objects.equals(id, other.id)
                && Objects.equals(identificador, other.identificador) && Objects.equals(logradouro, other.logradouro) && Objects.equals(nome, other.nome) && Objects.equals(nomeMunicipio, other.nomeMunicipio)
                && Objects.equals(numero, other.numero) && Objects.equals(pontoDeReferencia, other.pontoDeReferencia) && Objects.equals(ramal, other.ramal) && Objects.equals(telefone, other.telefone)
                && Objects.equals(tipoEquipamento, other.tipoEquipamento) && Objects.equals(unidadeFederacao, other.unidadeFederacao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bairro, codigoMunicipio, complemento, coordenada, diasSemana, email, endereco, horasDia, id, identificador, logradouro, nome, nomeMunicipio, numero, pontoDeReferencia, ramal, telefone, tipoEquipamento,
                unidadeFederacao);
    }

    @Override
    public String toString() {
        return String.format(
                "Equipamento [id=%s, identificador=%s, nome=%s, codigoMunicipio=%s, nomeMunicipio=%s, unidadeFederacao=%s, logradouro=%s, endereco=%s, numero=%s, complemento=%s, bairro=%s, pontoDeReferencia=%s, telefone=%s, ramal=%s, email=%s, coordenada=%s, diasSemana=%s, horasDia=%s, tipoEquipamento=%s]",
                id, identificador, nome, codigoMunicipio, nomeMunicipio, unidadeFederacao, logradouro, endereco, numero, complemento, bairro, pontoDeReferencia, telefone, ramal, email, coordenada, diasSemana, horasDia, tipoEquipamento);
    }

}
